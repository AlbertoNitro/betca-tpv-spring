package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.OrderBody;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderBodyRepositoryIT {

    @Autowired
    private OrderBodyRepository orderBodyRepository;
    
    private OrderBody orderBody;
    
    @Before
    public void before() {
        this.orderBody = new OrderBody("2018-11","2018-1","article1");
        this.orderBodyRepository.save(this.orderBody);
    }
    
    @Test
    public void findByIdTest() {
        assertEquals("2018-11",this.orderBodyRepository.findById("2018-11").getId());
        assertEquals("article1",this.orderBodyRepository.findById("2018-11").getId_article());
    }
    
    @Test
    public void findAllTest() {
        assertEquals(true, this.orderBodyRepository.findAll().size()>0);
    }

}
