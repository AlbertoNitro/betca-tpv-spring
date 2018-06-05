package es.upm.miw.restControllers;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.restControllers.CashierClosureResource;
import es.upm.miw.restControllers.TicketResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TicketResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Before
    public void testGetCashierClosureLast() {
        restService.loginAdmin().restBuilder(new RestBuilder<CashierLastOutputDto>()).clazz(CashierLastOutputDto.class)
                .path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST).get().build();
    }

    @Test
    public void testCreateTicket() {
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), true));
        ticketCreationInputDto.getShoppingCart().add(
                new ShoppingDto("8400000000017", "descrip-a1", new BigDecimal("20"), 1, BigDecimal.ZERO, new BigDecimal("20"), false));
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketCashNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("-1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketCardNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("-1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketVoucherNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("-1"), new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketShoppingNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), null, "Nota asociada al ticket");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketShoppingEmptyException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>(), "Nota asociada al ticket");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testGetTicket() {
        byte[] bodyResponse = restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS)
                .path(TicketResource.ID_ID).expand("201801121").clazz(byte[].class).get().build();
        assertNotNull(bodyResponse);
    }

    @Test
    public void testGetTicketIdNotFoundException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).path(TicketResource.ID_ID)
                .expand("not").clazz(byte[].class).get().log().build();
    }

}
