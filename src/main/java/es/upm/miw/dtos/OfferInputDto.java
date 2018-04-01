package es.upm.miw.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import es.upm.miw.documents.core.Offer;

public class OfferInputDto {
	@NotNull (message = "You must assign a name-code to the offer.")
    private String code;
	@NotNull(message = "The percentage is necessary.")
	@DecimalMax("100.0") 
	@DecimalMin("0.00") 
    private Float percentage;
	@NotNull (message = "Offer must have an expiration date.")
    private Date expiration;
    private String description;

    public OfferInputDto() {
    }

	public OfferInputDto(String code, Float percentage, Date expiration) {
		this(code, percentage, expiration, null);
    }
	
	public OfferInputDto(String code, Float percentage, Date expiration, String description) {
		this();
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
  	    String expirationStr = new SimpleDateFormat(Offer.DATE_FORMAT).format(expiration.getTime());
		return "OfferInputDto [code=" + code + ", expiration=" + expirationStr + ", percentage="
				+ percentage + ", description=" + description + " ]";
	}
}
