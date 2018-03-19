package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Order;
import es.upm.miw.repositories.core.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderControllerIT {
    private OrderController orderController;
    private Order order;
    private OrderRepository orderRepository;
    
    @Before
    public void Before() {
        this.orderController = new OrderController();
        this.order = new Order("2018-1","1",new Date());
        this.orderRepository.save(order);
    }
    
    @Test
    public void readOrderTest() {
        assertEquals("2018-1",this.orderController.readOrder("2018-1").getId());
    }

}
