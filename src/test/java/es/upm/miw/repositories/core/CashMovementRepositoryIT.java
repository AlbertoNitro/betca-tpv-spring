package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.CashMovement;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashMovementRepositoryIT {

    @Autowired
    private CashMovementRepository cashMovementRepository;

    @Test
    public void testFindByCreationDateGreaterThan() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2018-01-06");
        List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(date);
		assertEquals(666.666, cashMovementList.get(0).getValue().doubleValue(), 10 - 10);
    }
}
