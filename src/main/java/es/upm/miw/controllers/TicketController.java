package es.upm.miw.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
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
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.services.PdfService;
import es.upm.miw.services.StatisticsDataService;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

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
        ticket.setDebt(ticket.getTicketTotal().subtract(ticketCreationDto.getCash()).subtract(ticketCreationDto.getCard())
                .subtract(ticketCreationDto.getVoucher()));
        ticket.setNote(ticketCreationDto.getNote());
        this.ticketRepository.save(ticket);
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
        List<Ticket> ticketList = this.ticketRepository.findByCreationDateBetween(start, end);
        List<TicketDto> ticketListDto = new ArrayList<TicketDto>();
        for (Ticket ticket : ticketList) {
            TicketDto ticketOutputDto = new TicketDto();
            ticketOutputDto.setId(ticket.getId());
            ticketListDto.add(ticketOutputDto);
        }
        return ticketListDto;
    }

    public Optional<byte[]> updateTicket(String id, TicketDto ticketDto) {
        Ticket ticket = this.ticketRepository.findOne(id);
        assert ticket != null;
        ticket.setDebt(ticketDto.getDebt());
        ticket.setNote(ticketDto.getNote());
        for (int i = 0; i < ticket.getShoppingList().length; i++) {
            int amountDifference = ticket.getShoppingList()[i].getAmount() - ticketDto.getShoppingList().get(i).getAmount();
            if (amountDifference > 0) {
                ticket.getShoppingList()[i].setAmount(ticketDto.getShoppingList().get(i).getAmount());
                Article article = ticket.getShoppingList()[i].getArticle();
                article.setStock(article.getStock() + amountDifference);
                this.articleRepository.save(article);
            }

            if (ticketDto.getShoppingList().get(i).isCommitted()) {
                ticket.getShoppingList()[i].setShoppingState(ShoppingState.COMMITTED);
            }
        }
        User user = null;
        if (ticketDto.getUser() != null) {
            user = this.userRepository.findByMobile(ticketDto.getUser().getMobile());
        }
        ticket.setUser(user);
        this.ticketRepository.save(ticket);
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
}
