package es.upm.miw.resources;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.dtos.ShoppingDto;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";

    @RequestMapping(method = RequestMethod.POST)
    public void createTicket(@RequestBody List<ShoppingDto> shoppingCart) {
        System.out.println(">>>>>> TicketResource:" + shoppingCart);
    }

}
