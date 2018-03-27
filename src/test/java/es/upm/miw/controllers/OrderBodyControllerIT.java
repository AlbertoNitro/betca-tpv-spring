package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.OrderBodyDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderBodyControllerIT {

    @Autowired
    private OrderBodyController orderBodyController;

    @Before
    public void Before() {
        OrderBodyDto orderBodyDto = new OrderBodyDto("OB1", "2018-1", "article1", "descrip-a1");
        this.orderBodyController.createOrderBody(orderBodyDto);
        assertEquals("OB1", this.orderBodyController.findById("OB1").getId());
    }

    @Test
    public void findOrderBodyDtoByIdTest() {
        assertEquals("OB1", this.orderBodyController.findOrderBodyDtoById("OB1").getId());
        assertEquals("article1", this.orderBodyController.findOrderBodyDtoById("OB1").getId_article());
        assertEquals("descrip-a1", this.orderBodyController.findOrderBodyDtoById("OB1").getArticle_name());
    }
    
    @Test
    public void findOrderBodyByIdTest() {
        assertEquals(true, this.orderBodyController.findAllByIdOrder("2018-1").size()>0);
    }

    public OrderBodyControllerIT() {
        // TODO Auto-generated constructor stub
    }

}
