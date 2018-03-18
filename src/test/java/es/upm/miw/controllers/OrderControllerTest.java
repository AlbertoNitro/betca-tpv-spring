package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderControllerTest {
    private OrderController orderController;
    
    @Before
    public void Before() {
        this.orderController = new OrderController();
    }
    
    @Test
    public void FirstOrderControllerTest() {
        assertEquals("1",this.orderController.readProvider("1").getId());
    }

}
