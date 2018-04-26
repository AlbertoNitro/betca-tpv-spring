package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Property;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.InvoiceCreationInputDto;
import es.upm.miw.dtos.InvoiceOutputDto;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.PropertyRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

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

    public Optional<byte[]> createInvoice(InvoiceCreationInputDto invoiceCreationInputDto) {
        User user = this.userRepository.findByMobile(invoiceCreationInputDto.getMobile());
        Ticket ticket = this.ticketRepository.findOne(invoiceCreationInputDto.getTicketId());

        if (user != null && ticket != null) {
            BigDecimal taxBaseTotal = BigDecimal.ZERO.setScale(4);
            BigDecimal uno = new BigDecimal("1").setScale(4);
            for (Shopping shopping : ticket.getShoppingList()) {
                switch (shopping.getArticle().getTax()) {
                case GENERAL:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaGeneral.add(uno), 4, RoundingMode.HALF_UP))
                            .setScale(2, RoundingMode.HALF_UP);
                    break;
                case REDUCED:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaReduced.add(uno), 4, RoundingMode.HALF_UP))
                            .setScale(2, RoundingMode.HALF_UP);
                    break;
                case SUPER_REDUCED:
                    taxBaseTotal = taxBaseTotal.add(shopping.getShoppingTotal().divide(ivaSuperReduced.add(uno), 4, RoundingMode.HALF_UP))
                            .setScale(2, RoundingMode.HALF_UP);
                    break;
                case FREE:
                    break;
                default:
                    assert false;
                }
            }
            Invoice invoice = new Invoice(this.nextInvoiceId(), taxBaseTotal,
                    ticket.getTotal().subtract(taxBaseTotal).setScale(2, RoundingMode.HALF_UP), user, ticket);
            this.invoiceRepository.save(invoice);

            return pdfService.generateInvioce(invoice);
        } else {
            return Optional.empty();
        }
    }

    private int nextInvoiceId() {
        int nextId = 1;
        Invoice invoice = invoiceRepository.findFirstByOrderByCreationDateDesc();
        if (invoice == null) {
            Property property = this.propertyRepository.findOne("miw.invoice.initial");
            if (property != null) {
                nextId = Integer.parseInt(property.getValue());
            }
        } else {
            nextId = invoice.simpleId() + 1;
        }
        return nextId;
    }

    public Optional<InvoiceOutputDto> read(String id) {
        Invoice invoice = this.invoiceRepository.findOne(id);
        if (invoice != null) {
            return Optional.of(new InvoiceOutputDto(invoice));
        } else {
            return Optional.empty();
        }
    }

    public List<InvoiceOutputDto> findBetweenDates(Date start, Date end) {
        List<Invoice> invoiceList = this.invoiceRepository.findByCreationDateBetween(start, end);
        List<InvoiceOutputDto> invoiceListDto = new ArrayList<InvoiceOutputDto>();
        for (Invoice ticket : invoiceList) {
            invoiceListDto.add(new InvoiceOutputDto(ticket.getId()));
        }
        return invoiceListDto;
    }

    public List<InvoiceOutputDto> findByMobile(String mobile) {
        List<InvoiceOutputDto> invoiceListDto = new ArrayList<InvoiceOutputDto>();
        User user = this.userRepository.findByMobile(mobile);
        if (user != null) {
            List<Ticket> ticketList = this.ticketRepository.findByUserOrderByCreationDateDesc(user);
            List<Invoice> invoiceList = this.invoiceRepository.findByTicketIn(ticketList);
            for (Invoice invoice : invoiceList) {
                invoiceListDto.add(new InvoiceOutputDto(invoice.getId()));
            }
        }
        return invoiceListDto;
    }

    public InvoiceOutputDto findByTicket(String ticketId) {
        Ticket ticket = this.ticketRepository.findOne(ticketId);
        if (ticket != null) {
            Invoice invoice = this.invoiceRepository.findByTicket(ticket);
            if (invoice != null) {
                return new InvoiceOutputDto(invoice);
            }
        }
        return new InvoiceOutputDto();
    }

}
