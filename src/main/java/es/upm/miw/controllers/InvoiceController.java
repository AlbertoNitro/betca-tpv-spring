package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Shopping;
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

    @Value("${miw.tax.general}")
    private BigDecimal ivaGeneral;

    @Value("${miw.tax.reduced}")
    private BigDecimal ivaReduced;

    @Value("${miw.tax.super.reduced}")
    private BigDecimal ivaSuperReduced;

    @Value("${miw.tax.free}")
    private BigDecimal ivaFree;

    public Optional<byte[]> createInvoice(TicketCreationInputDto ticketCreationDto) {
        Optional<Ticket> ticket = this.ticketController.createTicket(ticketCreationDto);
        if (ticket.isPresent()) {
            Invoice invoice = new Invoice(this.nextInvoiceId(), ticketRepository.findOne(ticket.get().getId()));
            this.invoiceRepository.save(invoice);

            BigDecimal taxBaseTotal = BigDecimal.ZERO.setScale(4);
            BigDecimal uno = new BigDecimal("1").setScale(4);
            for (Shopping shopping : ticket.get().getShoppingList()) {
                switch (shopping.getArticle().getTax()) {
                case GENERAL:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaGeneral.add(uno), 4, RoundingMode.HALF_UP));
                    break;
                case REDUCED:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaReduced.add(uno), 4, RoundingMode.HALF_UP));
                    break;
                case SUPER_REDUCED:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaSuperReduced.add(uno), 4, RoundingMode.HALF_UP));
                    break;
                case FREE:
                    break;
                default:
                    assert false;
                }
            }
            return pdfService.generateInvioce(invoice, taxBaseTotal);
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
