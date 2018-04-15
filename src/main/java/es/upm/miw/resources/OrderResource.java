package es.upm.miw.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.OrderController;
import es.upm.miw.dtos.OrderBaseOutputDto;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.resources.exceptions.OrderException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
@RestController
@RequestMapping(OrderResource.ORDER)
public class OrderResource {

    public static final String ORDER = "/orders";

    public static final String ID_ID = "/{id}";

    public static final String CLOSING_DATE = "/closingDate";

    @Autowired
    private OrderController orderController;

    @GetMapping
    public List<OrderBaseOutputDto> readAll() {
        return this.orderController.readAll();
    }

    @PostMapping
    public void create(@Valid @RequestBody OrderDto OrderDto) throws OrderException {
        Optional<String> error = this.orderController.create(OrderDto);
        if (error.isPresent()) {
            throw new OrderException(error.get());
        }
    }

    @GetMapping(value = ID_ID)
    public OrderDto readOne(@PathVariable String id) throws OrderException {
        return this.orderController.readOne(id).orElseThrow(() -> new OrderException("Id not found. " + id));
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) throws OrderException {
        Optional<String> error = this.orderController.delete(id);
        if (error.isPresent()) {
            throw new OrderException(error.get());
        }
    }

    @PutMapping(value = ID_ID)
    public void update(@PathVariable String id, @Valid @RequestBody OrderDto OrderDto) throws OrderException {
        Optional<String> error = this.orderController.update(id, OrderDto);
        if (error.isPresent()) {
            throw new OrderException(error.get());
        }
    }

    @PostMapping(value = ID_ID + CLOSING_DATE)
    public void orderEntry(@PathVariable String id, @Valid @RequestBody OrderDto OrderDto) throws OrderException {
        Optional<String> error = this.orderController.orderEntry(id, OrderDto);
        if (error.isPresent()) {
            throw new OrderException(error.get());
        }
    }
}
