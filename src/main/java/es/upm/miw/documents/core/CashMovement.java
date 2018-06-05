package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CashMovement {

    private BigDecimal value;

    private String comment;

    private Date creationDate;

    @DBRef
    private User user;

    public CashMovement() {
        this.creationDate = new Date();
    }

    public CashMovement(BigDecimal value, String comment, User user) {
        this();
        this.value = value;
        this.comment = comment;
        this.user = user;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String creationTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(creationDate.getTime());
        return "CashMovement [value=" + value + ", comment=" + comment + ", creationDate=" + creationTime + ", user=" + user + "]";
    }

}
