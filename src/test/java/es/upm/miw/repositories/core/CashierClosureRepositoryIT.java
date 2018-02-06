package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testFindOne() {
        CashierClosure cashierClosure = new CashierClosure();
        cashierClosureRepository.save(cashierClosure);
        assertEquals(cashierClosure.getId(),cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getId());
        cashierClosureRepository.delete(cashierClosure);
    }

}
