package es.upm.miw.dtos;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class OfferInputDto {
	@NotNull (message = "Debes asignar un nombre a la oferta.")
    private String code;
	@NotNull(message = "Un porcentaje descuento es necesario.")
	@DecimalMax("100.0") 
	@DecimalMin("0.00") 
    private Float percentage;
	@NotNull (message = "La oferta debe tener una fecha de finalización.")
    private Date expiration;
    private String description;

    public OfferInputDto() {
        // Empty for framework
    }

	public OfferInputDto(String code, Float percentage, Date expiration) {
		this(code, percentage, expiration, null);
    }
	
	public OfferInputDto(String code, Float percentage, Date expiration, String description) {
        this.code = code;
        this.percentage = percentage;
        this.expiration = expiration;
        this.description = description;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Override
	public String toString() {
		return "Offer [code=" + code + ", expiration=" + expiration + ", percentage="
				+ percentage + ", description=" + description + " ]";
	}
}
