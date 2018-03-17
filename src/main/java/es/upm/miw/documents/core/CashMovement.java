package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CashMovement {

    private BigDecimal value;
    
    private String comment;

    private Date creationDate;

    public CashMovement() {
        this(new BigDecimal(0));
    }

    public CashMovement(BigDecimal value) {
        this.creationDate = new Date();
        this.comment = "";
        this.setValue(value);
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

    @Override
    public String toString() {
        String creationTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(creationDate.getTime());
        return "CashMovement [value=" + value.doubleValue() + ", comment=" 
        							+ comment + ", creationDate=" + creationTime + "]";
    }

}
