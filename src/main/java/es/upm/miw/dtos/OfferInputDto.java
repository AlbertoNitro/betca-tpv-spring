package es.upm.miw.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OfferInputDto {
    private String code;
    private Float percentage;
    private Date expiration;
    private Date creationDate;
    private String description;

    public OfferInputDto() {
        // Empty for framework
    }

	public OfferInputDto(String code, Float percentage, Date expiration, String description) {
        super();
        this.code = code;
        this.percentage = percentage;
        this.expiration = expiration;
        this.creationDate = new Date();
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
		if (creationDate != null) {
			date = new SimpleDateFormat("dd-MMM-yyyy").format(creationDate.getTime());
		}
		return "Offer [code=" + code + ", creationDate=" + date + ", expiration=" + expiration + ", percentage="
				+ percentage + ", description=" + description + " ]";
	}
}
