package es.upm.miw.documents.core;

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

    @DBRef
    private Ticket ticket;

    public Invoice() {
        creationDate = new Date();
    }

    public Invoice(int idOfYear, Ticket ticket) {
        this();
        this.id = new SimpleDateFormat(DATE_FORMAT).format(new Date()) + idOfYear;
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

    public Date getCreated() {
        return creationDate;
    }

    public void setCreated(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
        return (id == ((Invoice) obj).id);
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(creationDate.getTime());
        return "Invoice [id=" + id + ", created=" + date + ", ticket=" + ticket + "]";
    }

    public static void main(String[] args) {
        System.out.println(new Invoice(1,null));
    }
}
