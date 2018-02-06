package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CashierClosure {

    @Id
    private String id;

    private Date openingDate;

    private BigDecimal initialCash;

    private BigDecimal salesCash;

    private BigDecimal salesCard;

    private BigDecimal finalCash;

    private BigDecimal deposit;

    private BigDecimal extract;

    private String comment;

    private Date closureDate;

    public CashierClosure() {
        this.openingDate = new Date();
        this.closureDate = null;
    }

    public CashierClosure(BigDecimal initialCash) {
        this();
        this.initialCash = initialCash;
    }

    public void close(BigDecimal finalCash, BigDecimal salesCard, String comment) {
        this.finalCash = finalCash;
        this.salesCard = salesCard;
        this.comment = comment;
        this.closureDate = new Date();
    }

    public boolean isClosed() {
        return getClosureDate() != null;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getExtract() {
        return extract;
    }

    public void setExtract(BigDecimal extract) {
        this.extract = extract;
    }

    public BigDecimal getInitialCash() {
        return initialCash;
    }

    public BigDecimal getSalesCash() {
        return salesCash;
    }

    public BigDecimal getSalesCard() {
        return salesCard;
    }

    public BigDecimal getFinalCash() {
        return finalCash;
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
        return (id == ((CashierClosure) obj).id);
    }

    @Override
    public String toString() {
        return "CashierClosure [id=" + id + ", openingDate=" + openingDate + ", initialCash=" + initialCash + ", salesCash=" + salesCash
                + ", salesCard=" + salesCard + ", finalCash=" + finalCash + ", deposit=" + deposit + ", extract=" + extract + ", comment="
                + comment + ", closureDate=" + closureDate + "]";
    }

}
