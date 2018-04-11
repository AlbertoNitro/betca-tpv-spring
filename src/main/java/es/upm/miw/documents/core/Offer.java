package es.upm.miw.documents.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Offer {
    public static String DATE_FORMAT = "yyyy-MM-dd";

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    private Date expiration;

    private Float percentage;

    private Date creationDate;

    private String description;

    public Offer() {
        this.creationDate = new Date();
    }

    public Offer(String code, Float percentage, Date expiration) {
        this(code, percentage, expiration, null);
    }

    public Offer(String code, Float percentage, Date expiration, String description) {
        this();
        this.code = code;
        this.percentage = percentage;
        this.expiration = expiration;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return code.equals(((Offer) obj).code);
    }

    @Override
    public String toString() {
        String creationDateStr = new SimpleDateFormat(Offer.DATE_FORMAT).format(creationDate.getTime());
        String expirationStr = new SimpleDateFormat(Offer.DATE_FORMAT).format(expiration.getTime());
        return "Offer [id =" + id + "code=" + code + ", creationDate=" + creationDateStr + ", expiration=" + expirationStr + ", percentage="
                + percentage + ", description=" + description + " ]";
    }
}
