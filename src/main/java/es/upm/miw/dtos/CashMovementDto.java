package es.upm.miw.dtos;

import java.math.BigDecimal;

import es.upm.miw.documents.core.CashMovement;

public class CashMovementDto {
	
	private BigDecimal value;
	
	private String comment;
	
	public CashMovementDto() {
		// Empty for framework
	}
	
	public CashMovementDto(BigDecimal value, String comment) {
        this.value = value;
        this.comment = comment;
    }
	
	public CashMovementDto(CashMovement cashMovement) {
		this.value = cashMovement.getValue();
		this.comment = cashMovement.getComment();
    }
	
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "CashMovementDto [value=" + value + ", comment=" + comment + "]";
	}
	
	

}
