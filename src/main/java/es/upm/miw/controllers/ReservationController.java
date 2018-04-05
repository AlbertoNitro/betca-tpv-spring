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
import es.upm.miw.documents.core.Reservation;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.ReservationCreationInputDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ReservationRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.services.PdfService;

@Controller
public class ReservationController {
	
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PdfService pdfService;
    
    
    private int nextId() {
        int nextId = 1;
        Reservation reservation = reservationRepository.findFirstByOrderByCreationDateDescIdDesc();
        if (reservation != null) {
            Date startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (reservation.getCreationDate().compareTo(startOfDay) >= 0) {
                nextId = reservation.simpleId() + 1;
            }
        }
        return nextId;
    }
	
    private Optional<Reservation> createReservation(ReservationCreationInputDto reservationCreationDto) {
        User user = this.userRepository.findByMobile(reservationCreationDto.getUserMobile());
        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingDto shoppingDto : reservationCreationDto.getShoppingCart()) {
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
        Reservation reservation = new Reservation(this.nextId(), reservationCreationDto.getCash(), shoppingList.toArray(new Shopping[0]), user);
        this.reservationRepository.save(reservation);
        return Optional.of(reservation);
    }

    public Optional<byte[]> createReservationAndPdf(ReservationCreationInputDto reservationCreationDto) {
        Optional<Reservation> reservation = this.createReservation(reservationCreationDto);
        if (reservation.isPresent()) {
            return pdfService.generateReservation(reservation.get());
        } else {
            return Optional.empty();
        }
    }

}
