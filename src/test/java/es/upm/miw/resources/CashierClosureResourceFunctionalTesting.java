package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.client.HttpClientErrorException;

import es.upm.miw.controllers.CashierClosureController;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierClosureLastOutputDto;
import es.upm.miw.dtos.CashierClosureSearchOutputDto;
import es.upm.miw.services.DatabaseSeederService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureResourceFunctionalTesting {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private RestService restService;
	
	@Autowired
	private DatabaseSeederService databaseSeederService;

	@Autowired
	private CashierClosureController cashierClosureController;

	private void createCashier() {
		restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES).post().build();
	}

	private void closeCashier() {
		CashierClosureInputDto cashierClosureDto = new CashierClosureInputDto(new BigDecimal(23), new BigDecimal(10),
				"test");
		restService.loginAdmin().restBuilder().path(CashierClosureResource.CASHIER_CLOSURES)
				.path(CashierClosureResource.LAST).body(cashierClosureDto).patch().build();
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
		CashierClosureLastOutputDto cashierClosureLastDto = restService.loginAdmin()
				.restBuilder(new RestBuilder<CashierClosureLastOutputDto>()).clazz(CashierClosureLastOutputDto.class)
				.path(CashierClosureResource.CASHIER_CLOSURES).path(CashierClosureResource.LAST).get().build();
		assertTrue(cashierClosureLastDto.isClosed());
	}

	@Test
	public void tesCashierClosuretDates() throws ParseException {
		Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 00:00:00");
		Date dateFinish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-12-01 11:59:59");
		List<CashierClosureSearchOutputDto> searchOutputDtos = Arrays
				.asList(restService.loginAdmin().restBuilder(new RestBuilder<CashierClosureSearchOutputDto[]>())
						.clazz(CashierClosureSearchOutputDto[].class).path(CashierClosureResource.CASHIER_CLOSURES)
						.path(CashierClosureResource.SEARCH).param("dateStart", "2018-01-01 00:00:00")
						.param("dateFinish", "2018-12-01 99:99:99").get().build());
		List<CashierClosureSearchOutputDto> searchOutputDtos_ = cashierClosureController
				.getAllSalesCashierClosure(startDate, dateFinish);
		assertEquals(searchOutputDtos_.size(), searchOutputDtos.size());
	}

	@Test
	public void tesCashierClosureTotals() throws IOException, ParseException {
		cashierClosureController.createCashierClosure(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-01 00:00:00"));		
		CashierClosureSearchOutputDto totalOutputDtos = restService.loginAdmin()
				.restBuilder(new RestBuilder<CashierClosureSearchOutputDto>())
				.clazz(CashierClosureSearchOutputDto.class).path(CashierClosureResource.CASHIER_CLOSURES)
				.path(CashierClosureResource.TOTALS).get().build();
		assertEquals(-649.232, totalOutputDtos.getTotalCard().doubleValue(), 10 - 10);
		assertEquals(808.232, totalOutputDtos.getTotalCash().doubleValue(), 10 - 10);
		this.databaseSeederService.deleteAllAndCreateAdmin();
		this.databaseSeederService.seedDatabase("tpv-db-test.yml");
	}
}
