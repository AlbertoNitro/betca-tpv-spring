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
        return (id.equals(((CashierClosure) obj).id));
    }

    @Override
    public String toString() {
        return "CashierClosure [id=" + id + ", openingDate=" + openingDate + ", initialCash=" + initialCash + ", salesCash=" + salesCash
                + ", salesCard=" + salesCard + ", usedVouchers=" + usedVouchers + ", finalCash=" + finalCash + ", deposit=" + deposit
                + ", withdrawal=" + withdrawal + ", comment=" + comment + ", closureDate=" + closureDate + "]";
    }

}
