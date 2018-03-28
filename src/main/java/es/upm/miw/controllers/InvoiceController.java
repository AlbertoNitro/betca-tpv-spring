package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.InvoiceDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
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
    private UserRepository userRepository;

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
            if (invoice.getCreationDated().compareTo(startOfDay) >= 0) {
                nextId = invoice.simpleId() + 1;
            }
        }
        return nextId;
    }

    public Optional<InvoiceDto> read(String id) {
        Invoice invoice = this.invoiceRepository.findOne(id);
        if (invoice != null) {
            return Optional.of(new InvoiceDto(invoice));
        } else {
            return Optional.empty();
        }
    }

    public List<InvoiceDto> findBetweenDates(Date start, Date end) {
        List<Invoice> invoiceList = this.invoiceRepository.findByCreationDateBetween(start, end);
        List<InvoiceDto> invoiceListDto = new ArrayList<InvoiceDto>();
        for (Invoice ticket : invoiceList) {
            invoiceListDto.add(new InvoiceDto(ticket.getId()));
        }
        return invoiceListDto;
    }

    public List<InvoiceDto> findByMobile(String mobile) {
        List<InvoiceDto> invoiceListDto = new ArrayList<InvoiceDto>();
        User user = this.userRepository.findByMobile(mobile);
        if (user != null) {
            List<Ticket> ticketList = this.ticketRepository.findByUserOrderByCreationDateDesc(user);
            List<Invoice> invoiceList = this.invoiceRepository.findByTicketIn(ticketList);
            for (Invoice invoice : invoiceList) {
                invoiceListDto.add(new InvoiceDto(invoice.getId()));
            }
        }
        return invoiceListDto;
    }

}
