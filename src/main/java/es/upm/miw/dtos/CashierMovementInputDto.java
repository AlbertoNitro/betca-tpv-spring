package es.upm.miw.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import es.upm.miw.documents.core.CashMovement;

public class CashierMovementInputDto {
	
	private BigDecimal value;
	
    @NotNull
	private String comment;
	
    @NotNull
	private String authorMobile;
	
	public CashierMovementInputDto() {
		// Empty for framework
	}
	
	public CashierMovementInputDto(BigDecimal value, String comment, String authorMobile) {
        super();
        this.value = value;
        this.comment = comment;
        this.authorMobile = authorMobile;
    }

    public CashierMovementInputDto(CashMovement cashMovement) {
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

	public String getAuthorMobile() {
        return authorMobile;
    }

    public void setAuthorMobile(String authorMobile) {
        this.authorMobile = authorMobile;
    }

    @Override
    public String toString() {
        return "CashMovementDto [value=" + value + ", comment=" + comment + ", authorMobile=" + authorMobile + "]";
    }

}
