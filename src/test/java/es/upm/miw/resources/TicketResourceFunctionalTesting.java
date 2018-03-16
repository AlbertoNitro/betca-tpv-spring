package es.upm.miw.resources;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketUpdationInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TicketResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testCreateTicket() {
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), false));
        ticketCreationInputDto.getShoppingCart()
                .add(new ShoppingDto("1", "various", new BigDecimal("100"), 2, new BigDecimal("50.00"), new BigDecimal("100"), true));
        ticketCreationInputDto.getShoppingCart().add(
                new ShoppingDto("article2", "descrip-a2", new BigDecimal("10"), 100, new BigDecimal("0"), new BigDecimal("1000"), true));
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketCashNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("-1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketCardNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("-1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketVoucherNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("-1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketShoppingNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), null);
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateTicketShoppingEmptyException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testUpdateAmountAndStateTicketIdNotFoundException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        List<Integer> listAmounts = new ArrayList<Integer>();
        List<Boolean> listCommitteds = new ArrayList<Boolean>();
        listAmounts.add(1);
        listAmounts.add(2);
        listCommitteds.add(true);
        listCommitteds.add(true);
        TicketUpdationInputDto ticketUpdationInputDto = new TicketUpdationInputDto(listAmounts, listCommitteds);
        this.restService.loginAdmin().restBuilder(new RestBuilder<>()).path(TicketResource.TICKETS).path(TicketResource.ID).expand("20180112-1")
                .body(ticketUpdationInputDto).patch().build();
    }

    @Test
    public void testGetTicket() {
        byte[] bodyResponse = restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).path(TicketResource.ID).expand("20180112-3").clazz(byte[].class).get().build();
        assertNotNull(bodyResponse);
    }

    @Test
    public void testGetTicketIdNotFoundException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).path(TicketResource.ID).expand("20180112-6").clazz(byte[].class).get().log().build();
    }

}
