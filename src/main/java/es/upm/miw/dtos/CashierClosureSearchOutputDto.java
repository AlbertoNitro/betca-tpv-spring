package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CashierClosureSearchOutputDto {

	private BigDecimal salesCard;

	private BigDecimal salesCash;
	
    @JsonInclude(Include.NON_NULL)
	private Date closureDate;
	
	private BigDecimal totalCard;
	
	private BigDecimal totalCash;
	
	
	public CashierClosureSearchOutputDto() {
		// Empty for framework
	}

	public CashierClosureSearchOutputDto(BigDecimal salesCard, BigDecimal salesCash, Date closureDate) {
		this.salesCard = salesCard;
		this.salesCash = salesCash;
		this.closureDate = closureDate;
	}
	
	public CashierClosureSearchOutputDto(BigDecimal totalCard, BigDecimal totalCash) {
		this.totalCard = totalCard;
		this.totalCash = totalCash;
	}

	public BigDecimal getSalesCard() {
		return salesCard;
	}

	public void setSalesCard(BigDecimal salesCard) {
		this.salesCard = salesCard;
	}

	public BigDecimal getSalesCash() {
		return salesCash;
	}

	public void setSalesCash(BigDecimal salesCash) {
		this.salesCash = salesCash;
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

	public Date getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	@Override
	public String toString() {
		return "CashierClosureStatisOutputDto [salesCard=" + salesCard + ", salesCash=" + salesCash + ", closureDate="
				+ closureDate + ", totalCard=" + totalCard + ", totalCash=" + totalCash + "]";
	}

}
