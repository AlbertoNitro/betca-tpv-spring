package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import es.upm.miw.documents.core.CashierClosure;

public class ClosedCashierOutputDto {

    private String id;

    private Date openingDate;

    private BigDecimal initialCash;

    private BigDecimal usedVouchers;

    private BigDecimal salesCard;

    private BigDecimal salesCash;

    private BigDecimal deposit;

    private BigDecimal withdrawal;

    private String comment;

    private BigDecimal finalCash;

    private Date closureDate;

    public ClosedCashierOutputDto() {
    }

    public ClosedCashierOutputDto(CashierClosure cashierClosure) {
        this.id = cashierClosure.getId();
        this.openingDate = cashierClosure.getOpeningDate();
        this.initialCash = cashierClosure.getInitialCash();
        this.usedVouchers = cashierClosure.getUsedVouchers();
        this.salesCard = cashierClosure.getSalesCard();
        this.salesCash = cashierClosure.getSalesCash();
        this.deposit = cashierClosure.getDeposit();
        this.withdrawal = cashierClosure.getWithdrawal();
        this.finalCash = cashierClosure.getFinalCash();
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
        return initialCash.setScale(2, RoundingMode.HALF_UP);
    }

    public void setInitialCash(BigDecimal initialCash) {
        this.initialCash = initialCash;
    }

    public BigDecimal getUsedVouchers() {
        return usedVouchers.setScale(2, RoundingMode.HALF_UP);
    }

    public void setUsedVouchers(BigDecimal usedVouchers) {
        this.usedVouchers = usedVouchers;
    }

    public BigDecimal getSalesCard() {
        return salesCard.setScale(2, RoundingMode.HALF_UP);
    }

    public void setSalesCard(BigDecimal salesCard) {
        this.salesCard = salesCard;
    }

    public BigDecimal getSalesCash() {
        return salesCash.setScale(2, RoundingMode.HALF_UP);
    }

    public void setSalesCash(BigDecimal salesCash) {
        this.salesCash = salesCash;
    }

    public BigDecimal getFinalCash() {
        return finalCash.setScale(2, RoundingMode.HALF_UP);
    }

    public void setFinalCash(BigDecimal finalCash) {
        this.finalCash = finalCash;
    }

    public BigDecimal getDeposit() {
        return deposit.setScale(2, RoundingMode.HALF_UP);
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getWithdrawal() {
        return withdrawal.setScale(2, RoundingMode.HALF_UP);
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
        return "ClosedCashierOutputDto [getId()=" + getId() + ", getOpeningDate()=" + getOpeningDate() + ", getInitialCash()="
                + getInitialCash() + ", getUsedVouchers()=" + getUsedVouchers() + ", getSalesCard()=" + getSalesCard() + ", getSalesCash()="
                + getSalesCash() + ", getFinalCash()=" + getFinalCash() + ", getDeposit()=" + getDeposit() + ", getWithdrawal()="
                + getWithdrawal() + ", getComment()=" + getComment() + ", getClosureDate()=" + getClosureDate() + "]";
    }

}
