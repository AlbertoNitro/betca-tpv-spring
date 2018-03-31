package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

import es.upm.miw.documents.core.CashierClosure;

public class CashierClosureClosedOutputDto {

    private String id;

    private Date openingDate;

    private BigDecimal initialCash;

    private BigDecimal salesCash;

    private BigDecimal salesCard;

    private BigDecimal deposit;

    private BigDecimal withdrawal;

    private String comment;

    private Date closureDate;

    public CashierClosureClosedOutputDto() {
    }

    public CashierClosureClosedOutputDto(CashierClosure cashierClosure) {
        this.id = cashierClosure.getId();
        this.openingDate = cashierClosure.getOpeningDate();
        this.initialCash = cashierClosure.getInitialCash();
        this.salesCash = cashierClosure.getSalesCash();
        this.salesCard = cashierClosure.getSalesCard();
        this.deposit = cashierClosure.getDeposit();
        this.withdrawal = cashierClosure.getWithdrawal();
        this.comment = cashierClosure.getComment();
        this.closureDate = cashierClosure.getClosureDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getInitialCash() {
        return initialCash;
    }

    public void setInitialCash(BigDecimal initialCash) {
        this.initialCash = initialCash;
    }

    public BigDecimal getSalesCash() {
        return salesCash;
    }

    public void setSalesCash(BigDecimal salesCash) {
        this.salesCash = salesCash;
    }

    public BigDecimal getSalesCard() {
        return salesCard;
    }

    public void setSalesCard(BigDecimal salesCard) {
        this.salesCard = salesCard;
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

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    @Override
    public String toString() {
        return "CashierClosureClosedOutputDto [id=" + id + ", openingDate=" + openingDate + ", initialCash=" + initialCash + ", salesCash="
                + salesCash + ", salesCard=" + salesCard + ", deposit=" + deposit + ", withdrawal=" + withdrawal + ", comment=" + comment
                + ", closureDate=" + closureDate + "]";
    }

}
