package es.upm.miw.resources;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TicketController;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketUpdationInputDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";
    
    public static final String ID = "/{id}";

    @Autowired
    private TicketController ticketController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public @ResponseBody byte[] createTicket(@Valid @RequestBody TicketCreationInputDto ticketCreationDto) throws FieldInvalidException {
        Optional<byte[]> pdf = this.ticketController.createTicket(ticketCreationDto);
        if (!pdf.isPresent()) {
            throw new FieldInvalidException("Article exception");
        } else {
            return pdf.get();
        }
    }
    
    @RequestMapping(value = ID, method = RequestMethod.PATCH)
    public void updateAmountAndStateTicket(@PathVariable(value="id") String id, @RequestBody TicketUpdationInputDto ticketUpdationInputDto) throws FieldInvalidException {
    }
}
