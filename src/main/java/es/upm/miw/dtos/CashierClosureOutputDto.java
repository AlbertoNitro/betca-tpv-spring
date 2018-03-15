package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class CashierClosureOutputDto {
	
	private BigDecimal salesCard;
	
	private BigDecimal salesCash;
	
	private Date closureDate;
	
	
	public CashierClosureOutputDto() {
		// Empty for framework
	}
	
	public CashierClosureOutputDto(BigDecimal salesCard, BigDecimal salesCash, Date closureDate) {
		this.salesCard = salesCard;
		this.salesCash = salesCash;
		this.closureDate = closureDate;
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

	public Date getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	@Override
	public String toString() {
		return "CashierClosureStatisOutputDto [salesCard=" + salesCard + ", salesCash=" + salesCash + ", closureDate="
				+ closureDate + "]";
	}

}
