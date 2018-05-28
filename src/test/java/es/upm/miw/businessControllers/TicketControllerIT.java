package es.upm.miw.businessControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessControllers.CashierClosureController;
import es.upm.miw.businessControllers.TicketController;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.exceptions.PdfException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TicketControllerIT {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private CashierClosureController cashierClosureController;
    
    @Before
    public void before() {
        this.cashierClosureController.readCashierClosureLast();
    }

    @Test
    public void testCreateTicket() throws NotFoundException, PdfException {
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("70"), BigDecimal.ZERO, BigDecimal.ZERO,
                new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), true));
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("8400000000017", "descrip-a1", new BigDecimal("20"), 2, BigDecimal.ZERO, new BigDecimal("20"), false));
        this.ticketController.createTicketAndPdf(ticketCreationInputDto);
        Ticket ticket1 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        this.ticketController.createTicketAndPdf(ticketCreationInputDto);
        Ticket ticket2 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
        assertEquals(ticket1.simpleId() + 1, ticket2.simpleId());
        this.ticketRepository.delete(ticket1);
        this.ticketRepository.delete(ticket2);
    }

    @Test
    public void testGetTicket() throws NotFoundException {
       this.ticketController.read("201801121");
       this.ticketController.read("201801122");
       this.ticketController.read("201801123");
    }

    @Test
    public void testFindByIdArticleDateBetween() {
        assertNotNull(this.ticketController.findByIdArticleDatesBetween("article1", new Date(), new Date()));
    }

}
