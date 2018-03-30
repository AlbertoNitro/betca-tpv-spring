package es.upm.miw.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;
 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Offer;

@JsonInclude(Include.NON_NULL)
public class OfferOutputDto {
	private String id;
    private String code;
    private Float percentage;
    private Date expiration;
    private Date creationDate;
    private String description;

    public OfferOutputDto() {
        // Empty for framework
    }

	public OfferOutputDto(Offer offer) {
        this.id = offer.getId();
        this.code = offer.getCode();
        this.percentage = offer.getPercentage();
        this.expiration = offer.getExpiration();
        this.creationDate = offer.getCreationDate();
        this.description = offer.getDescription();
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
  	    String creationDateStr = new SimpleDateFormat(Offer.DATE_FORMAT).format(creationDate.getTime());
  	    String expirationStr = new SimpleDateFormat(Offer.DATE_FORMAT).format(expiration.getTime());
		return "OfferOutputDto [code=" + code + ", creationDate=" + creationDateStr + ", expiration=" + expirationStr + ", percentage="
				+ percentage + ", description=" + description + " ]";
	}
}
