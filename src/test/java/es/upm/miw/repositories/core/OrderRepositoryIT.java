package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.Provider;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;
    
    private Order order;
    
    private Provider provider;

    @Before
    public void before() {
        //this.provider = new Provider("9993","Company-pruebas","calle prueba","65652","9111","nota",true);
        this.order = new Order("2018-1",new Date());
        this.orderRepository.save(order);
    }
    @Test
    public void findOrderByIdTest() {
        assertEquals("2018-1",this.orderRepository.findById("2018-1").getId());
        //assertEquals("9993",this.orderRepository.findById("99").getProvider().getId());
    }
    

}
