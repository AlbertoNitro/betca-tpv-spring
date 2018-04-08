package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Order;
import es.upm.miw.dtos.OrderBaseOutputDto;
import es.upm.miw.repositories.core.OrderRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderBaseOutputDto> readAll() {
        List<Order> orderList = this.orderRepository.findAll();
        List<OrderBaseOutputDto> orderDtoOutputList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoOutputList.add(new OrderBaseOutputDto(order));
        }
        return orderDtoOutputList;
    }

}
