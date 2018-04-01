package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Invoice;

@JsonInclude(Include.NON_NULL)
public class InvoiceOutputDto {

    private String id;

    private Date creationDate;

    private BigDecimal baseTax;

    private BigDecimal tax;

    private UserDto user;

    private TicketDto ticket;

    public InvoiceOutputDto() {
    }

    public InvoiceOutputDto(String id) {
        this.id = id;
    }

    public InvoiceOutputDto(Invoice invoice) {
        this(invoice.getId());
        this.creationDate = invoice.getCreationDated();
        this.baseTax = invoice.getBaseTax();
        this.tax = invoice.getTax();
        this.user = new UserDto(invoice.getUser());
        this.ticket = new TicketDto(invoice.getTicket());
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

    public BigDecimal getBaseTax() {
        return baseTax;
    }

    public void setBaseTax(BigDecimal baseTax) {
        this.baseTax = baseTax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "InvoiceDto [id=" + id + ", creationDate=" + creationDate + ", baseTax=" + baseTax + ", tax=" + tax + ", user=" + user
                + ", ticket=" + ticket + "]";
    }

}
