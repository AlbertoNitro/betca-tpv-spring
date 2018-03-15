package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.dtos.CashierClosureOutputDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CashierClosureRepositoryIT {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;
    
    CashierClosure cashierClosure1;
    CashierClosure cashierClosure2;
    CashierClosure cashierClosure3;

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
        assertEquals(cashierClosure1.getId(),cashierClosureRepository.findFirstByOrderByClosureDateDesc().getId());
    }
    
    @Test
    public void testFindDateStatics() throws ParseException  {
    	 cashierClosure3 = new CashierClosure();
         cashierClosure3.close(new BigDecimal("500"), new BigDecimal("200"),  new BigDecimal("100"),  new BigDecimal("0"), "");
         cashierClosureRepository.save(cashierClosure3);
        Date date = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2018-03-13 00:00:00.000");
        Date dateEnd = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2018-03-13 99:99:99.999");
        System.out.println(cashierClosure3.getClosureDate()+ "----------"  + cashierClosure3.getOpeningDate());
    	List<CashierClosureOutputDto> lista = cashierClosureRepository.findAllStatics(date.toGMTString(), date.toGMTString());
    	System.out.println(lista);
    }
    
    @After
    public void after() {
        cashierClosureRepository.delete(cashierClosure1);
        cashierClosureRepository.delete(cashierClosure2);
    }

}
