package es.upm.miw.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OfferInputDto {
    private String code;
    private Float percentage;
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
		String date = "null";
		return "Offer [code=" + code + ", expiration=" + expiration + ", percentage="
				+ percentage + ", description=" + description + " ]";
	}
}
