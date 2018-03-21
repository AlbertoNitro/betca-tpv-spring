package es.upm.miw.controllers;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.services.DatabaseSeederService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureControllerIT {

	@Autowired
	private CashierClosureController cashierClosureController;

	@Autowired
	private DatabaseSeederService databaseSeederService;

	@Test
	public void testClose() throws IOException {
		cashierClosureController.createCashierClosure();
		CashierClosureInputDto cashierClosureDto = new CashierClosureInputDto(new BigDecimal("100"),
				new BigDecimal("50"), "testClose");
		cashierClosureController.close(cashierClosureDto);
		this.databaseSeederService.deleteAllAndCreateAdmin();
		this.databaseSeederService.seedDatabase("tpv-db-test.yml");

	}

	@Test
	public void testGetSalesCashierClosure() {
		assertNotNull(this.cashierClosureController.getAllSalesCashierClosure(new Date(), new Date()));
	}

	@Test
	public void testGetTotalCardAndCashCashierClosure() {
		assertNotNull(this.cashierClosureController.getTotalCardAndCash());
	}

}
