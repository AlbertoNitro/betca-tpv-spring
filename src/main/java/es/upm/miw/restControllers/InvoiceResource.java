package es.upm.miw.restControllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.InvoiceController;
import es.upm.miw.dtos.InvoiceCreationInputDto;
import es.upm.miw.dtos.InvoiceOutputDto;
import es.upm.miw.exceptions.PdfException;
import es.upm.miw.exceptions.InvoiceException;
import es.upm.miw.exceptions.NotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(InvoiceResource.INVOICES)
public class InvoiceResource {
    public static final String INVOICES = "/invoices";

    public static final String ID_ID = "/{id}";

    public static final String SEARCH_DATE = "/search/date";

    public static final String SEARCH_MOBILE = "/search/mobile";

    public static final String SEARCH_TICKET = "/search/ticket";

    @Autowired
    private InvoiceController invoiceController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public byte[] createInvoice(@Valid @RequestBody InvoiceCreationInputDto invoiceCreationInputDto)
            throws MethodArgumentNotValidException, NotFoundException, InvoiceException, PdfException {
        return this.invoiceController.createInvoice(invoiceCreationInputDto);
    }

    @GetMapping(value = ID_ID)
    public InvoiceOutputDto readInvoice(@PathVariable String id) throws NotFoundException {
        return this.invoiceController.read(id);
    }

    @GetMapping(value = SEARCH_DATE)
    public List<InvoiceOutputDto> findBetweenDates(@RequestParam long start, @RequestParam long end) {
        return this.invoiceController.findBetweenDates(new Date(start), new Date(end));
    }

    @GetMapping(value = SEARCH_MOBILE)
    public List<InvoiceOutputDto> findByMobile(@RequestParam String mobile) {
        return this.invoiceController.findByMobile(mobile);
    }

    @GetMapping(value = SEARCH_TICKET)
    public InvoiceOutputDto findByTicket(@RequestParam String ticketId) {
        return this.invoiceController.findByTicket(ticketId);
    }

}
