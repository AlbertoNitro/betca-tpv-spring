package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TicketResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testCreateTicketCashNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("-1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).accept(MediaType.APPLICATION_PDF).post().build();
    }

    @Test
    public void testCreateTicketCardNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("-1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).accept(MediaType.APPLICATION_PDF).post().build();
    }

    @Test
    public void testCreateTicketVoucherNegativeException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("-1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).accept(MediaType.APPLICATION_PDF).post().build();
    }

    @Test
    public void testCreateTicketShoppingNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), null);
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).accept(MediaType.APPLICATION_PDF).post().build();
    }

    @Test
    public void testCreateTicketShoppingEmptyException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1"), new BigDecimal("1"),
                new BigDecimal("1"), new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(TicketResource.TICKETS).body(ticketCreationInputDto)
                .clazz(byte[].class).accept(MediaType.APPLICATION_PDF).post().build();
    }


}
