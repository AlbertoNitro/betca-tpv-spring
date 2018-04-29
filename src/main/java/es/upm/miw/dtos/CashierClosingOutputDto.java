package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CashierClosingOutputDto {

    //TODO no entiendo que este aqui
    private BigDecimal salesCard;

    //TODO no entiendo que este aqui
    private Date closureDate;

    private BigDecimal totalVoucher;
    
    private BigDecimal totalCard;

    private BigDecimal totalCash;

    private BigDecimal salesTotal;
    
    private BigDecimal finalCash;

    public CashierClosingOutputDto() {
        // Empty for framework
    }

    //TODO no entiendo que este aqui
    public CashierClosingOutputDto(BigDecimal salesCard, BigDecimal finalCash, Date closureDate) {
        this.salesCard = salesCard;
        this.finalCash = finalCash;
        this.closureDate = closureDate;
    }

    public CashierClosingOutputDto(BigDecimal totalCash, BigDecimal totalCard, BigDecimal totalVoucher, BigDecimal salesTotal) {
        this.totalCash = totalCash;
        this.totalCard = totalCard;
        this.totalVoucher = totalVoucher;
        this.salesTotal = salesTotal;
    }

    public BigDecimal getSalesCard() {
        return salesCard.setScale(2, RoundingMode.HALF_UP);
    }

    public void setSalesCard(BigDecimal salesCard) {
        this.salesCard = salesCard;
    }

    public BigDecimal getFinalCash() {
        return finalCash.setScale(2, RoundingMode.HALF_UP);
    }

    public void setFinalCash(BigDecimal salesCash) {
        this.finalCash = salesCash;
    }

    public BigDecimal getTotalCard() {
        return totalCard.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalCard(BigDecimal totalCard) {
        this.totalCard = totalCard;
    }

    public BigDecimal getTotalCash() {
        return totalCash.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalCash(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public BigDecimal getTotalVoucher() {
        return totalVoucher.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalVoucher(BigDecimal totalVoucher) {
        this.totalVoucher = totalVoucher;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public BigDecimal getSalesTotal() {
        return salesTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    @Override
    public String toString() {
        return "CashierClosingOutputDto [getSalesCard()=" + getSalesCard() + ", getFinalCash()=" + getFinalCash() + ", getTotalCard()="
                + getTotalCard() + ", getTotalCash()=" + getTotalCash() + ", getTotalVoucher()=" + getTotalVoucher() + ", getClosureDate()="
                + getClosureDate() + ", getSalesTotal()=" + getSalesTotal() + "]";
    }

}
