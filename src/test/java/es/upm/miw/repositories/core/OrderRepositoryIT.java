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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;
    
    private Order order;

    @Before
    public void before() {
        this.order = new Order("2018-1","1",new Date());
        this.orderRepository.save(order);
    }
    @Test
    public void findOrderByIdTest() {
        assertEquals("2018-1",this.orderRepository.findById("2018-1").getId());
        assertEquals("1",this.orderRepository.findById("2018-1").getId_provider());
    }
    

}
