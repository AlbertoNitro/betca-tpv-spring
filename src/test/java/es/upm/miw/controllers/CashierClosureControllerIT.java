package es.upm.miw.controllers;


import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.input.CashierClosureDto;
import es.upm.miw.services.DatabaseSeederService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureControllerIT {

    @Autowired
    private CashierClosureController cashierClosureController;

//    @Autowired
//    private CashierClosureRepository cashierClosureRepository;
    
    @Autowired
    private DatabaseSeederService databaseSeederService;
    

    @Test
    public void testClose() throws IOException {
        cashierClosureController.createCashierClosure();
        CashierClosureDto cashierClosureDto = new CashierClosureDto(new BigDecimal("100"), new BigDecimal("50"), "testClose");
        cashierClosureController.close(cashierClosureDto);
        //assertEquals(new BigDecimal("153.20"),cashierClosureRepository.findAll().get(0).getSalesCash());
        this.databaseSeederService.deleteAllAndCreateAdmin();
        this.databaseSeederService.seedDatabase("tpv-db-test.yml");

    }

}