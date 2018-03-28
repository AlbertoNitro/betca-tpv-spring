package es.upm.miw.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.InvoiceController;
import es.upm.miw.dtos.InvoiceDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;
import es.upm.miw.resources.exceptions.InvoiceIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(InvoiceResource.INVOICES)
public class InvoiceResource {
    public static final String INVOICES = "/invoices";
    
    public static final String ID_ID = "/{id}";

    @Autowired
    private InvoiceController invoiceController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public @ResponseBody byte[] createInvoice(@Valid @RequestBody TicketCreationInputDto ticketCreationDto) throws FieldInvalidException {
        return this.invoiceController.createInvoice(ticketCreationDto).orElseThrow(() -> new FieldInvalidException("Article exception"));
    }
    
    @GetMapping(value = ID_ID)
    public InvoiceDto readInvice(@PathVariable String id) throws InvoiceIdNotFoundException {
        return this.invoiceController.read(id).orElseThrow(() -> new InvoiceIdNotFoundException(id));
    }

}
