package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class InvoiceControllerIT {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private InvoiceController invoiceController;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateInvioce() {
        User user = new User("131313131", "Pablo", "Pablo", "1104456987", "Direccion", "");
        this.userRepository.save(user);

        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1150"), new BigDecimal("0"),
                new BigDecimal("0"), new ArrayList<ShoppingDto>());
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), false));
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 2, new BigDecimal("50.00"), new BigDecimal("100"), true));
        ticketCreationInputDto.getShoppingCart().add(
                new ShoppingDto("article2", "descrip-a2", new BigDecimal("10"), 100, new BigDecimal("0"), new BigDecimal("1000"), true));
        ticketCreationInputDto.setUserMobile(user.getMobile());
        this.invoiceController.createInvoice(ticketCreationInputDto);
        Ticket ticket = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        Invoice invoice = this.invoiceRepository.findFirstByOrderByCreationDateDescIdDesc();
        assertEquals(ticket.getId(), invoice.getTicket().getId());
        this.ticketRepository.delete(ticket);
        this.invoiceRepository.delete(invoice);
        this.userRepository.delete(user);
    }

}
