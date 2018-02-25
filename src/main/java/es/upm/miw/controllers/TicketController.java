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
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.services.PdfService;

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

    public Optional<byte[]> createTicket(TicketCreationInputDto ticketCreationDto) {
        User user = this.userRepository.findByMobile(ticketCreationDto.getUserMobile());
        List<Shopping> shoppingList = new ArrayList<Shopping>();
        for (ShoppingDto item : ticketCreationDto.getShoppingCart()) {
            Article article = this.articleRepository.findOne(item.getCode());
            if (article == null) {
                return Optional.empty();
            }
            Shopping shopping = new Shopping(item.getAmount(), item.getDiscount(), article);
            if (item.isCommitted()) {
                shopping.setShoppingState(ShoppingState.COMMITTED);
            } else {
                shopping.setShoppingState(ShoppingState.OPENED);
            }
            shoppingList.add(shopping);
        }
        Ticket ticket = new Ticket(this.nextId(), ticketCreationDto.getCash(), shoppingList.toArray(new Shopping[0]), user);
        this.ticketRepository.save(ticket);
        return pdfService.generateTicket(ticket);
    }

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

}
