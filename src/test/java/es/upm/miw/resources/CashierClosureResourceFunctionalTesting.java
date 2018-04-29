package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

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
import org.springframework.web.client.HttpClientErrorException;

import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.CashierMovementInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private void createCashier() {
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).post().build();
    }

    private void closeCashier() {
        CashierClosureInputDto cashierClosureDto = new CashierClosureInputDto(new BigDecimal(23), new BigDecimal(10), "test");
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST)
                .body(cashierClosureDto).patch().build();
    }

    @Test
    public void testCreateAndCreateCashierClosureException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        this.closeCashier();
    }

    @Test
    public void testCloseCashierClosureException() {
        this.createCashier();
        try {
            this.createCashier();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
        }
        restService.reLoadTestDB();
    }

    @Test
    public void testGetCashierClosureLast() {
        CashierLastOutputDto cashierClosureLastDto = restService.loginAdmin()
                .restBuilder(new RestBuilder<CashierLastOutputDto>()).clazz(CashierLastOutputDto.class)
                .path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST).get().build();
        assertTrue(cashierClosureLastDto.isClosed());
    }

    // @Test
    //No se pueden realizar métodos en el controlador sólo para probar
    public void tesCashierClosureTotals() throws IOException, ParseException {
//        cashierClosureController.createCashierClosure(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-01 00:00:00"));
//        CashierClosureSearchOutputDto totalOutputDtos = restService.loginAdmin()
//                .restBuilder(new RestBuilder<CashierClosureSearchOutputDto>()).clazz(CashierClosureSearchOutputDto.class)
//                .path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.TOTALS).get().build();
//        assertEquals(-649.232, totalOutputDtos.getTotalCard().doubleValue(), 10 - 10);
//        assertEquals(808.232, totalOutputDtos.getTotalCash().doubleValue(), 10 - 10);
//        this.databaseSeederService.deleteAllAndCreateAdmin();
//        this.databaseSeederService.seedDatabase("tpv-db-test.yml");
    }

    @Test
    public void testCreateCashMovement() {
        this.createCashier();
        CashierMovementInputDto cashMovementDto = new CashierMovementInputDto(new BigDecimal("1"), "test", "666666000");
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST)
                .path(CashierClosureResource.MOVEMENTS).body(cashMovementDto).post().build();
        restService.reLoadTestDB();
    }
    
    @Test
    public void testCreateCashMovementClosedException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        CashierMovementInputDto cashMovementDto = new CashierMovementInputDto(new BigDecimal("1"), "test", "666666000");
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST)
                .path(CashierClosureResource.MOVEMENTS).body(cashMovementDto).post().build();
    }

    @Test
    public void testCreateCashMovementNullValue() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        CashierMovementInputDto cashMovementDto = new CashierMovementInputDto(null, "test", "666666000");
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST)
                .path(CashierClosureResource.MOVEMENTS).body(cashMovementDto).post().build();
    }

    @Test
    public void testCreateCashMovementNullComment() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        CashierMovementInputDto cashMovementDto = new CashierMovementInputDto(new BigDecimal("1"), null, "666666000");
        restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST)
                .path(CashierClosureResource.MOVEMENTS).body(cashMovementDto).post().build();
    }

}
