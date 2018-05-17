package es.upm.miw.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.OrderController;
import es.upm.miw.dtos.OrderBaseOutputDto;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.exceptions.OrderException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(OrderResource.ORDER)
public class OrderResource {

    public static final String ORDER = "/orders";

    public static final String ID_ID = "/{id}";

    public static final String CLOSING_DATE = "/closing-date";

    @Autowired
    private OrderController orderController;

    @PostMapping
    public void create(@Valid @RequestBody OrderDto orderDto) throws MethodArgumentNotValidException, NotFoundException {
        this.orderController.create(orderDto);
    }

    @PutMapping(value = ID_ID)
    public void update(@PathVariable String id, @Valid @RequestBody OrderDto OrderDto)
            throws MethodArgumentNotValidException, NotFoundException, OrderException {
        this.orderController.update(id, OrderDto);
    }

    @PostMapping(value = ID_ID + CLOSING_DATE)
    public void orderEntry(@PathVariable String id, @Valid @RequestBody OrderDto OrderDto)
            throws MethodArgumentNotValidException, NotFoundException, OrderException {
        this.orderController.orderEntry(id, OrderDto);
    }

    @GetMapping(value = ID_ID)
    public OrderDto read(@PathVariable String id) throws NotFoundException {
        return this.orderController.read(id);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) throws OrderException {
        this.orderController.delete(id);
    }

    @GetMapping
    public List<OrderBaseOutputDto> readAll() {
        return this.orderController.readAll();
    }

}
