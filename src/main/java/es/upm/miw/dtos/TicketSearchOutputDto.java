package es.upm.miw.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class TicketSearchOutputDto {
	
    @JsonInclude(Include.NON_NULL)
	private Date creationDate;
	
    private Integer shoppingCartAcount;
	
	public TicketSearchOutputDto() {
		// Empty for framework
	}
	
	public TicketSearchOutputDto(Date creationDate, Integer shoppingCartAcount) {
		this.creationDate = creationDate;
		this.shoppingCartAcount = shoppingCartAcount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getShoppingCart() {
		return shoppingCartAcount;
	}

	public void setShoppingCart(Integer shoppingCartAcount) {
		this.shoppingCartAcount = shoppingCartAcount;
	}

	@Override
	public String toString() {
		return "TicketSearchOutputDto [creationDate=" + creationDate + ", shoppingCart=" + shoppingCartAcount + "]";
	}
	

}
