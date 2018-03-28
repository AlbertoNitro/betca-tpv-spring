package es.upm.miw.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.services.PdfService;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketController ticketController;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createInvoice(TicketCreationInputDto ticketCreationDto) {
        Optional<Ticket> ticket = this.ticketController.createTicket(ticketCreationDto);
        if (ticket.isPresent()) {
            Invoice invoice = new Invoice(this.nextInvoiceId(), ticketRepository.findOne(ticket.get().getId()));
            this.invoiceRepository.save(invoice);
            return pdfService.generateInvioce(invoice);
        } else {
            return Optional.empty();
        }
    }

    private int nextInvoiceId() {
        int nextId = 1;
        Invoice invoice = invoiceRepository.findFirstByOrderByCreationDateDescIdDesc();
        if (invoice != null) {
            Date startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (invoice.getCreated().compareTo(startOfDay) >= 0) {
                nextId = invoice.simpleId() + 1;
            }
        }
        return nextId;
    }

}
