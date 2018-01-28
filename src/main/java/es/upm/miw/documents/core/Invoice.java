package es.upm.miw.documents.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Invoice {

    @Id
    private int id;

    private Date creationDate;

    @DBRef
    private Ticket ticket;

    public Invoice() {
        creationDate = new Date();
    }

    public Invoice(int id, Ticket ticket) {
        this();
        this.id = id;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id;
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
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(creationDate.getTime());
        return "Invoice [id=" + id + ", created=" + date + ", ticket=" + ticket + "]";
    }

}
