package es.upm.miw.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.OrderController;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.resources.exceptions.OrderIdNotFoundException;

@RestController
@RequestMapping(OrderResource.ORDER)
public class OrderResource {

    public static final String ORDER = "/orders";

    public static final String ORDER_ID = "/{id}";

    @Autowired
    private OrderController orderController;

    public OrderResource() {
        // TODO Auto-generated constructor stub
    }

    @RequestMapping(value = ORDER_ID, method = RequestMethod.GET)
    public OrderDto readOrder(@PathVariable String id) throws OrderIdNotFoundException {
        return this.orderController.readOrderDto(id);
    }

    @GetMapping
    public List<OrderDto> readAll() {
        return this.orderController.readAll();
    }

}
