package es.upm.miw.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.CashMovementDto;
import es.upm.miw.repositories.core.CashMovementRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashMovementControllerIT {

	@Autowired
	private CashMovementController cashMovementController;

	private CashMovementDto cashMovementDto;

	@Autowired
	private CashMovementRepository cashMovementRepository;

	@Before
	public void before() {
		this.cashMovementDto = new CashMovementDto(new BigDecimal(32), "testRandom");
	}

	@Test
	public void create() {
		this.cashMovementController.createCashMovement(cashMovementDto);
		int size = this.cashMovementRepository.findAll().size();
		assertTrue(size > 0);
		assertEquals(this.cashMovementRepository.findAll().get(size - 1).getComment(), "testRandom");
	}

}
