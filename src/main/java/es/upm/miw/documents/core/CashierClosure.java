package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CashierClosure {
    
    @Id
    private String id;

    private BigDecimal amount;

    private Date openingDate;

    private Date closureDate;

    private String comment;

    public CashierClosure() {
        this.openingDate = new Date();
    }

    public void close(BigDecimal amount, String comment) {
        this.amount = amount;
        this.comment = comment;
        this.closureDate= new Date();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public String getComment() {
        return comment;
    }

}
