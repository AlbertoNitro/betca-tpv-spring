package es.upm.miw.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.OrderController;
import es.upm.miw.dtos.OrderBaseOutputDto;

@RestController
@RequestMapping(OrderResource.ORDER)
public class OrderResource {

    public static final String ORDER = "/orders";

    public static final String ORDER_ID = "/{id}";

    public static final String PROVIDER_ID = "/{id}";

    @Autowired
    private OrderController orderController;

    @GetMapping
    public List<OrderBaseOutputDto> readAll() {
        return this.orderController.readAll();
    }

}
