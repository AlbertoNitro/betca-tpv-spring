package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.CashierClosure;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureRepositoryIT {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;
    
    CashierClosure cashierClosure1;
    CashierClosure cashierClosure2;

    @Before
    public void before() {
        cashierClosure1 = new CashierClosure();
        cashierClosure1.close(new BigDecimal("100"), new BigDecimal("200"),  new BigDecimal("100"),  new BigDecimal("0"), "");
        cashierClosureRepository.save(cashierClosure1);
        cashierClosure2 = new CashierClosure();
        cashierClosureRepository.save(cashierClosure2);
        
    }

    @Test
    public void testFindFirstByOrderByOpeningDateDesc() {
        assertEquals(cashierClosure2.getId(),cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getId());
    }
    
    @Test
    public void testFindFirstByOrderByClosureDateDesc() {
        CashierClosure cashierClosure1 = new CashierClosure();
        cashierClosure1.close(new BigDecimal("100"), new BigDecimal("200"),  new BigDecimal("100"),  new BigDecimal("0"), "");
        cashierClosureRepository.save(cashierClosure1);
        CashierClosure cashierClosure2 = new CashierClosure();
        cashierClosureRepository.save(cashierClosure2);
        assertEquals(cashierClosure1.getId(),cashierClosureRepository.findFirstByOrderByClosureDateDesc().getId());
    }
    
    @After
    public void after() {
        cashierClosureRepository.delete(cashierClosure1);
        cashierClosureRepository.delete(cashierClosure2);
    }
    

}
