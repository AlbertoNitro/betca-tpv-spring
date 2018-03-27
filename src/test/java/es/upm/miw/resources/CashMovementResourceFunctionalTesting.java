package es.upm.miw.resources;

import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.CashMovementDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class CashMovementResourceFunctionalTesting {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private RestService restService;

	@Test
	public void testCreateCashMovement() {
		CashMovementDto cashMovementDto = new CashMovementDto(new BigDecimal("1"), "test");
		restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(CashMovementResource.CASH_MOVEMENTS)
				.body(cashMovementDto).clazz(byte[].class).post().build();
	}

	@Test
	public void testCreateCashMovementNullValue() {
		CashMovementDto cashMovementDto = new CashMovementDto(null, "test");
		byte[] bodyResponse = restService.loginAdmin().restBuilder(new RestBuilder<byte[]>())
				.path(CashMovementResource.CASH_MOVEMENTS).body(cashMovementDto).clazz(byte[].class).post().build();
		assertNull(bodyResponse);
	}

	@Test
	public void testCreateCashMovementNukkComment() {
		CashMovementDto cashMovementDto = new CashMovementDto(new BigDecimal("1"), null);
		byte[] bodyResponse = restService.loginAdmin().restBuilder(new RestBuilder<byte[]>())
				.path(CashMovementResource.CASH_MOVEMENTS).body(cashMovementDto).clazz(byte[].class).post().build();
		assertNull(bodyResponse);
	}
}
