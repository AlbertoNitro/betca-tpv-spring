package es.upm.miw.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.OrderBodyController;
import es.upm.miw.dtos.OrderBodyDto;
import es.upm.miw.resources.exceptions.OrderBodyIdNotFoundException;

@RestController
@RequestMapping(OrderBodyResource.ORDERBODY)
public class OrderBodyResource {

    public static final String ORDERBODY = "/orderbody";

    public static final String ORDERBODYOPTION = "/idbody";

    public static final String ORDERBODY_ID = "/{id}";

    @Autowired
    private OrderBodyController orderBodyController;

    public OrderBodyResource() {
        // TODO Auto-generated constructor stub
    }

    @RequestMapping(value = ORDERBODY_ID, method = RequestMethod.GET)
    public OrderBodyDto readOrderBody(@PathVariable String id) throws OrderBodyIdNotFoundException {
        return this.orderBodyController.findOrderBodyDtoById(id);
    }

    @PostMapping
    public void createOrderBody(@RequestBody OrderBodyDto orderBodyDto) {
        this.orderBodyController.createOrderBody(orderBodyDto);
    }

    @RequestMapping(value = ORDERBODYOPTION)
    public List<OrderBodyDto> findAllById(@RequestParam("id") String id) {
        return this.orderBodyController.findAllByIdOrder(id);

    }

}