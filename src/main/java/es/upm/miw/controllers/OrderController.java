package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public OrderController() {
        // TODO Auto-generated constructor stub
    }

    public void createOrder(OrderDto orderDto) {
        Order order = new Order(orderDto.getId(), orderDto.getProvider_id(), orderDto.getOrder_date());
        this.orderRepository.save(order);
    }

    public Order readOrder(String id) {
        return this.orderRepository.findById(id);
    }

    public List<OrderDto> readAll() {
        List<Order> orderList = this.orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
        for (Order order : orderList) {
            if (order.getId_provider() != null) {
                String company = providerRepository.findOne(order.getId_provider()).getCompany();
                orderDtoList.add( new OrderDto(order.getId(), order.getId_provider(), company));
            }
        }
        return orderDtoList;
    }

    public OrderDto readOrderDto(String id) {
        Order order = this.readOrder(id);
        Provider provider = this.providerRepository.findById(order.getId_provider());
        return new OrderDto(order, provider);
    }

}
