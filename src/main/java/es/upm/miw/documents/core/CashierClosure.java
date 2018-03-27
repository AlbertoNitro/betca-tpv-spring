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

    private BigDecimal usedVouchers;

    private BigDecimal finalCash;

    private BigDecimal deposit;

    private BigDecimal withdrawal;

    private String comment;

    private Date closureDate;

    public CashierClosure() {
        this.openingDate = new Date();
        this.closureDate = null;
        this.comment = "";
        this.deposit = new BigDecimal("0");
        this.withdrawal = new BigDecimal("0");
        this.initialCash = new BigDecimal("0");
    }

    public CashierClosure(BigDecimal initialCash) {
        this();
        this.initialCash = initialCash;
    }
    
    public CashierClosure(Date openingDate) {
        this();
        this.openingDate = openingDate;
    }

    public void deposit(BigDecimal cash, String comment) {
        this.deposit = this.deposit.add(cash);
        this.comment += "Deposit (" + cash.setScale(2).toString() + "): " + comment + ". ";
    }

    public void withdrawal(BigDecimal cash, String comment) {
        this.withdrawal = this.withdrawal.add(cash);
        this.comment += "Withdrawal (" + cash.setScale(2).toString() + "): " + comment + ". ";
    }

    public void close(BigDecimal finalCash, BigDecimal salesCash, BigDecimal salesCard, BigDecimal usedVouchers, String comment) {
        this.finalCash = finalCash;
        this.salesCash = salesCash;
        this.salesCard = salesCard;
        this.usedVouchers = usedVouchers;
        this.comment += comment + ". ";
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

    public BigDecimal getWithdrawal() {
        return withdrawal;
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

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((closureDate == null) ? 0 : closureDate.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
        result = prime * result + ((finalCash == null) ? 0 : finalCash.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((initialCash == null) ? 0 : initialCash.hashCode());
        result = prime * result + ((openingDate == null) ? 0 : openingDate.hashCode());
        result = prime * result + ((salesCard == null) ? 0 : salesCard.hashCode());
        result = prime * result + ((salesCash == null) ? 0 : salesCash.hashCode());
        result = prime * result + ((usedVouchers == null) ? 0 : usedVouchers.hashCode());
        result = prime * result + ((withdrawal == null) ? 0 : withdrawal.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CashierClosure other = (CashierClosure) obj;
        if (closureDate == null) {
            if (other.closureDate != null)
                return false;
        } else if (!closureDate.equals(other.closureDate))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (deposit == null) {
            if (other.deposit != null)
                return false;
        } else if (!deposit.equals(other.deposit))
            return false;
        if (finalCash == null) {
            if (other.finalCash != null)
                return false;
        } else if (!finalCash.equals(other.finalCash))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (initialCash == null) {
            if (other.initialCash != null)
                return false;
        } else if (!initialCash.equals(other.initialCash))
            return false;
        if (openingDate == null) {
            if (other.openingDate != null)
                return false;
        } else if (!openingDate.equals(other.openingDate))
            return false;
        if (salesCard == null) {
            if (other.salesCard != null)
                return false;
        } else if (!salesCard.equals(other.salesCard))
            return false;
        if (salesCash == null) {
            if (other.salesCash != null)
                return false;
        } else if (!salesCash.equals(other.salesCash))
            return false;
        if (usedVouchers == null) {
            if (other.usedVouchers != null)
                return false;
        } else if (!usedVouchers.equals(other.usedVouchers))
            return false;
        if (withdrawal == null) {
            if (other.withdrawal != null)
                return false;
        } else if (!withdrawal.equals(other.withdrawal))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CashierClosure [id=" + id + ", openingDate=" + openingDate + ", initialCash=" + initialCash + ", salesCash=" + salesCash
                + ", salesCard=" + salesCard + ", usedVouchers=" + usedVouchers + ", finalCash=" + finalCash + ", deposit=" + deposit
                + ", withdrawal=" + withdrawal + ", comment=" + comment + ", closureDate=" + closureDate + "]";
    }

}
