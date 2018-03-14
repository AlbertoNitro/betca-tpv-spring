package es.upm.miw.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VoucherDto {
	
	private BigDecimal value;
	private boolean used;
	
	public VoucherDto() {
		// Empty for framework
	}
	
	public VoucherDto(BigDecimal value) {
        this.value = value;
        this.used = false;
    }
	
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "VoucherDto [value=" + value + ", used=" + used + "]";
	}
	
	

}
