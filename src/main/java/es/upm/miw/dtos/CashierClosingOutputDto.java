package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CashierClosingOutputDto {

    private BigDecimal salesCard;

    private BigDecimal finalCash;

    private Date closureDate;

    private BigDecimal totalCash;

    private BigDecimal totalCard;

    private BigDecimal totalVoucher;

    private BigDecimal salesTotal;

    public CashierClosingOutputDto() {
        // Empty for framework
    }

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
        return salesCard;
    }

    public void setSalesCard(BigDecimal salesCard) {
        this.salesCard = salesCard;
    }

    public BigDecimal getFinalCash() {
        return finalCash;
    }

    public void setFinalCash(BigDecimal salesCash) {
        this.finalCash = salesCash;
    }

    public BigDecimal getTotalCard() {
        return totalCard;
    }

    public void setTotalCard(BigDecimal totalCard) {
        this.totalCard = totalCard;
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public BigDecimal getTotalVoucher() {
        return totalVoucher;
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
        return salesTotal;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    @Override
    public String toString() {
        return "CashierClosingOutputDto [salesCard=" + salesCard + ", finalCash=" + finalCash + ", closureDate=" + closureDate
                + ", totalCash=" + totalCash + ", totalCard=" + totalCard + ", totalVoucher=" + totalVoucher + ", salesTotal=" + salesTotal
                + "]";
    }

}
