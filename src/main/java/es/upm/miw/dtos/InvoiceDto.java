package es.upm.miw.dtos;

import java.util.Date;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Ticket;

public class InvoiceDto {

    private String id;

    private Date creationDate;

    private Ticket ticket;

    public InvoiceDto() {
   }

    public InvoiceDto(String id) {
        this.id = id;
    }

    public InvoiceDto(Invoice invoice) {
        this(invoice.getId());
        this.creationDate = invoice.getCreationDated();
        this.ticket=invoice.getTicket();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "InvoiceDto [id=" + id + ", creationDate=" + creationDate + ", ticket=" + ticket + "]";
    }

}
