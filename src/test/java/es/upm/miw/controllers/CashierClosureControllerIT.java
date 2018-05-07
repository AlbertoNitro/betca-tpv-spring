package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierMovementInputDto;
import es.upm.miw.repositories.core.CashMovementRepository;
import es.upm.miw.resources.exceptions.CashierException;
import es.upm.miw.resources.exceptions.NotFoundException;
import es.upm.miw.services.DatabaseSeederService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureControllerIT {

    @Autowired
    private CashierClosureController cashierClosureController;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @Autowired
    private CashMovementRepository cashMovementRepository;

    private CashierMovementInputDto cashMovementDto;

    @Before
    public void before() {
        this.cashMovementDto = new CashierMovementInputDto(new BigDecimal(32), "testRandom", "666666000");
    }

    @Test
    public void testClose() throws CashierException {
        cashierClosureController.createCashierClosure();
        CashierClosureInputDto cashierClosureDto = new CashierClosureInputDto(new BigDecimal("100"), new BigDecimal("50"), "testClose");
        cashierClosureController.close(cashierClosureDto);
        this.databaseSeederService.seedDatabase();
    }

    @Test
    public void testGetTotalCardAndCashCashierClosure() throws CashierException {
        cashierClosureController.createCashierClosure();
        assertNotNull(this.cashierClosureController.readTotalsFromLast());
        this.databaseSeederService.seedDatabase();
    }

    @Test
    public void create() throws CashierException, NotFoundException {
        cashierClosureController.createCashierClosure();
        this.cashierClosureController.createCashierMovement(cashMovementDto);
        int size = this.cashMovementRepository.findAll().size();
        assertTrue(size > 0);
        assertEquals(this.cashMovementRepository.findAll().get(size - 1).getComment(), "testRandom");
        this.databaseSeederService.seedDatabase();
    }

}
