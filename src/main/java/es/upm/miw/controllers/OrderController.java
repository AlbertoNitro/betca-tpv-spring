package es.upm.miw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.repositories.core.OrderRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    public OrderController() {
        // TODO Auto-generated constructor stub
    }

    public OrderDto readProvider(String id) {
        return new OrderDto();
    }

    public List<OrderDto> readAll() {
        return this.orderRepository.findOrderAll();

    }

}
