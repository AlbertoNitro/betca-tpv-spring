package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Invoice {

    private static final String DATE_FORMAT = "yyyy-";

    @Id
    private String id;

    private Date creationDate;

    private BigDecimal baseTax;

    private BigDecimal tax;

    @DBRef
    private Ticket ticket;

    @DBRef
    private User user;

    public Invoice() {
        creationDate = new Date();
    }

    public Invoice(int idOfYear, BigDecimal baseTax, BigDecimal tax, User user, Ticket ticket) {
        this();
        this.id = new SimpleDateFormat(DATE_FORMAT).format(new Date()) + idOfYear;
        this.baseTax = baseTax;
        this.tax = tax;
        this.user = user;
        this.ticket = ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getCreationDated() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int simpleId() {
        return Integer.parseInt(String.valueOf(id).substring(DATE_FORMAT.length()));
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (id.equals(((Invoice) obj).id));
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(creationDate.getTime());
        return "Invoice [id=" + id + ", created=" + date + ", ticket=" + ticket + "]";
    }

}
