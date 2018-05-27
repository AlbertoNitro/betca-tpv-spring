package es.upm.miw.businessControllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.businessServices.PdfService;
import es.upm.miw.businessServices.StatisticsDataService;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.OrderLine;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.dtos.IncomeComparision;
import es.upm.miw.dtos.NumProductsSoldDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.dtos.TicketSearchOutputDto;
import es.upm.miw.dtos.UserNotCommitedOutputDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.FamilyCompositeRepository;
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FamilyCompositeRepository familyCompositeRepository;

    @Autowired
    private CashierClosureRepository cashierClosureRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private StatisticsDataService statisticsDataService;

    private int nextId() {
        int nextId = 1;
        Ticket ticket = ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        if (ticket != null) {
            Date startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (ticket.getCreationDate().compareTo(startOfDay) >= 0) {
                nextId = ticket.simpleId() + 1;
            }
        }
        return nextId;
    }

    private Optional<Ticket> createTicket(TicketCreationInputDto ticketCreationDto) {
        User user = this.userRepository.findByMobile(ticketCreationDto.getUserMobile());
        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingDto shoppingDto : ticketCreationDto.getShoppingCart()) {
            Article article = this.articleRepository.findOne(shoppingDto.getCode());
            if (article == null) {
                return Optional.empty();
            }
            Shopping shopping = new Shopping(shoppingDto.getAmount(), shoppingDto.getDiscount(), article);
            if (shoppingDto.isCommitted()) {
                shopping.setShoppingState(ShoppingState.COMMITTED);
            } else {
                shopping.setShoppingState(ShoppingState.NOT_COMMITTED);
            }
            shoppingList.add(shopping);
            article.setStock(article.getStock() - shoppingDto.getAmount());
            this.articleRepository.save(article);
        }
        Ticket ticket = new Ticket(this.nextId(), ticketCreationDto.getCash(), shoppingList.toArray(new Shopping[0]), user);
        ticket.setDebt(ticket.getTotal().subtract(ticketCreationDto.getCash()).subtract(ticketCreationDto.getCard())
                .subtract(ticketCreationDto.getVoucher()));
        if (ticket.getDebt().signum() == -1) {
            ticket.setDebt(BigDecimal.ZERO);
        }
        ticket.setNote(ticketCreationDto.getNote());
        this.ticketRepository.save(ticket);
        // ----------------------------------------------
        CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        cashierClosure.addCash(ticketCreationDto.getCash());
        cashierClosure.addCard(ticketCreationDto.getCard());
        this.cashierClosureRepository.save(cashierClosure);

        return Optional.of(ticket);
    }

    public Optional<byte[]> createTicketAndPdf(TicketCreationInputDto ticketCreationDto) {
        Optional<Ticket> ticket = this.createTicket(ticketCreationDto);
        if (ticket.isPresent()) {
            return pdfService.generateTicket(ticket.get());
        } else {
            return Optional.empty();
        }
    }

    public boolean existTicket(String id) {
        Ticket ticket = this.ticketRepository.findOne(id);
        return ticket != null;
    }

    public List<TicketDto> findBetweenDates(Date start, Date end) {
        List<Ticket> ticketList = this.ticketRepository.findByCreationDateBetweenOrderByCreationDateDesc(start, end);
        List<TicketDto> ticketListDto = new ArrayList<TicketDto>();
        for (Ticket ticket : ticketList) {
            TicketDto ticketOutputDto = new TicketDto();
            ticketOutputDto.setId(ticket.getId());
            ticketListDto.add(ticketOutputDto);
        }
        return ticketListDto;
    }

    public Optional<byte[]> updateTicket(String id, TicketCreationInputDto ticketCreationInputDto) {
        Ticket ticket = this.ticketRepository.findOne(id);
        assert ticket != null;
        ticket.setDebt(ticket.getDebt().subtract(ticketCreationInputDto.getCash()).subtract(ticketCreationInputDto.getCard())
                .subtract(ticketCreationInputDto.getVoucher()));
        ticket.setCashDeposited(ticket.getCashDeposited().add(ticketCreationInputDto.getCash()));
        ticket.setNote(ticketCreationInputDto.getNote());
        for (int i = 0; i < ticket.getShoppingList().length; i++) {
            int amountDifference = ticket.getShoppingList()[i].getAmount() - ticketCreationInputDto.getShoppingCart().get(i).getAmount();
            if (amountDifference > 0) {
                ticket.setDebt(ticket.getDebt().subtract(ticket.getShoppingList()[i].getShoppingTotal())
                        .add(ticketCreationInputDto.getShoppingCart().get(i).getTotal()));
                ticket.getShoppingList()[i].setAmount(ticketCreationInputDto.getShoppingCart().get(i).getAmount());
                Article article = ticket.getShoppingList()[i].getArticle();
                article.setStock(article.getStock() + amountDifference);
                this.articleRepository.save(article);
            }
            if (ticketCreationInputDto.getShoppingCart().get(i).isCommitted()) {
                ticket.getShoppingList()[i].setShoppingState(ShoppingState.COMMITTED);
            }
        }
        User user = null;
        if (ticketCreationInputDto.getUserMobile() != null) {
            user = this.userRepository.findByMobile(ticketCreationInputDto.getUserMobile());
        }
        ticket.setUser(user);
        this.ticketRepository.save(ticket);
        CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        cashierClosure.addCash(ticketCreationInputDto.getCash());
        cashierClosure.addCard(ticketCreationInputDto.getCard());
        this.cashierClosureRepository.save(cashierClosure);
        return pdfService.generateTicket(ticket);
    }

    public Optional<TicketDto> read(String id) {
        Ticket ticket = this.ticketRepository.findOne(id);
        if (ticket != null) {
            return Optional.of(new TicketDto(ticket));
        } else {
            return Optional.empty();
        }
    }

    public List<TicketSearchOutputDto> findByIdArticleDatesBetween(String id, Date dateStart, Date dateFinish) {
        List<Ticket> ticketList = this.ticketRepository.findByIdArticleDatesBetween(id, dateStart, dateFinish);
        List<TicketSearchOutputDto> ticketListDto = new ArrayList<TicketSearchOutputDto>();
        for (Ticket ticket : ticketList) {
            for (Shopping shopping : ticket.getShoppingList()) {
                ticketListDto.add(new TicketSearchOutputDto(ticket.getCreationDate(), shopping.getAmount()));
            }
        }
        return ticketListDto;
    }

    public List<TicketDto> findByMobile(String mobile) {
        List<TicketDto> ticketListDto = new ArrayList<TicketDto>();
        User user = this.userRepository.findByMobile(mobile);
        if (user != null) {
            List<Ticket> ticketList = this.ticketRepository.findByUserOrderByCreationDateDesc(user);
            for (Ticket ticket : ticketList) {
                TicketDto ticketDto = new TicketDto();
                ticketDto.setId(ticket.getId());
                ticketListDto.add(ticketDto);
            }
        }
        return ticketListDto;
    }

    public Optional<TicketDto> findLastByMobile(String mobile) {
        User user = this.userRepository.findByMobile(mobile);
        if (user != null) {
            Ticket ticket = this.ticketRepository.findFirstByUserOrderByCreationDateDesc(user);
            if (ticket != null) {
                return Optional.of(new TicketDto(ticket));
            }
        }
        return Optional.empty();
    }

    public List<HistoricalProductOutPutDto> getHistoricalProductsDataBetweenDates(Date initDate, Date endDate) {

        return this.statisticsDataService.GetHistoricalData(initDate, endDate);
    }

    public List<NumProductsSoldDto> getNumProductsSold(Date initDate, Date endDate) {

        return this.statisticsDataService.GetNumProductsSold(initDate, endDate);
    }

    public List<IncomeComparision> getIncomeComparisionData(Date initDate, Date endDate) {

        return this.statisticsDataService.GetIncomeComparisionData(initDate, endDate);
    }

    public Optional<List<UserNotCommitedOutputDto>> findByOrderArticleNotCommited(String orderId) {
        Order order = this.orderRepository.findOne(orderId);
        if (order == null) {
            return Optional.empty();
        }
        Map<String, Integer> entry = new HashMap<>();
        List<UserNotCommitedOutputDto> userNotCommitedList = new ArrayList<>();
        for (OrderLine line : order.getOrderLine()) {
            entry.put(line.getArticle().getCode(), line.getFinalAmount());
        }
        List<Ticket> ticketList = this.ticketRepository.findByShoopingListArticleIdNotCommited(entry.keySet().toArray(new String[0]));
        for (Ticket ticket : ticketList) {
            UserNotCommitedOutputDto userNotCommited = new UserNotCommitedOutputDto();
            userNotCommited.setTicketId(ticket.getId());
            if (ticket.getUser() != null) {
                userNotCommited.setMobile(ticket.getUser().getMobile());
                userNotCommited.setUsername(ticket.getUser().getUsername());
            }
            for (Shopping shopping : ticket.getShoppingList()) {
                if (ShoppingState.NOT_COMMITTED.equals(shopping.getShoppingState())) {
                    if (entry.containsKey(shopping.getArticle().getCode())) {
                        if (shopping.getAmount() <= entry.get(shopping.getArticle().getCode())) {
                            entry.put(shopping.getArticle().getCode(), entry.get(shopping.getArticle().getCode()) - shopping.getAmount());
                            userNotCommited.addArticleEntry(shopping.getArticle().getReference());
                        } else {
                            userNotCommited.addArticleNotEntry(shopping.getArticle().getReference());
                        }
                    } else {
                        userNotCommited.addArticleNotEntry(shopping.getArticle().getReference());
                    }
                }
            }
            userNotCommitedList.add(userNotCommited);
        }
        return Optional.of(userNotCommitedList);
    }

    public List<UserNotCommitedOutputDto> findByTicketsNotCommited() {
        return generateUserNotCommitedOutputDto(this.ticketRepository.findByTicketsNotCommited());
    }

    private List<UserNotCommitedOutputDto> generateUserNotCommitedOutputDto(List<Ticket> ticketList) {
        List<UserNotCommitedOutputDto> userNotCommitedList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            UserNotCommitedOutputDto userNotCommited = new UserNotCommitedOutputDto();
            userNotCommited.setTicketId(ticket.getId());
            if (ticket.getUser() != null) {
                userNotCommited.setMobile(ticket.getUser().getMobile());
                userNotCommited.setUsername(ticket.getUser().getUsername());
            }
            for (Shopping shopping : ticket.getShoppingList()) {
                if (ShoppingState.NOT_COMMITTED.equals(shopping.getShoppingState())) {
                    userNotCommited.addArticleNotEntry(shopping.getArticle().getReference());
                } else {
                    userNotCommited.addArticleEntry(shopping.getArticle().getReference());
                }
            }
            userNotCommitedList.add(userNotCommited);
        }
        return userNotCommitedList;
    }

    public Optional<List<UserNotCommitedOutputDto>> findByFamilyIdNotCommited(String familyId) {
        FamilyComposite familyComposite = this.familyCompositeRepository.findOne(familyId);
        if (familyComposite == null) {
            return Optional.empty();
        }
        List<String> articlesIdList = familyComposite.getArticleIdList();
        return Optional.of(generateUserNotCommitedOutputDto(
                this.ticketRepository.findByShoopingListArticleIdNotCommited(articlesIdList.toArray(new String[0]))));
    }

}
