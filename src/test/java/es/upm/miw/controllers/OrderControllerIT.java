package es.upm.miw.controllers;

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
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.dtos.OrderDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderControllerIT {

    @Autowired
    private OrderController orderController;

    private Order order;

    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void Before() {
        this.order = new Order("2018-1", "provider1", new Date());
        this.orderRepository.save(this.order);
    }

    @Test
    public void readOrderTest() {
        assertEquals("2018-1", this.orderController.readOrder("2018-1").getId());
    }

    @Test
    public void createOrderTest() {
        Provider provider = new Provider();
        provider.setCompany("Adrian company");
        Order order = new Order("2018-2", provider.getId(), new Date());
        OrderDto orderDto = new OrderDto(order, provider);
        this.orderController.createOrder(orderDto);
        assertEquals("2018-2", this.orderController.readOrder("2018-2").getId());
    }

    @Test
    public void readAllTest() {
        assertEquals(true, this.orderController.readAll().size() > 0);
    }

}
