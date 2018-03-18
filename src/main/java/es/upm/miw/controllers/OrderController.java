package es.upm.miw.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import es.upm.miw.dtos.OrderDto;

@Controller
public class OrderController {

    public OrderController() {
        // TODO Auto-generated constructor stub
    }

    public OrderDto readProvider(String id) {
        return new OrderDto();
    }

}
