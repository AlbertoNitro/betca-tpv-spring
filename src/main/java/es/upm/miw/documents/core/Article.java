package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Article {

    @Id
    private String code;

    private Date registrationDate;

    private String reference;

    private String description;

    private BigDecimal retailPrice;

    private String image;

    private int stock;

    private BigDecimal wholesalePrice;

    private boolean discontinued;

    @DBRef
    private Provider provider;

    public Article() {
        this.registrationDate = new Date();
        this.discontinued = false;
    }

    public Article(String code, String reference, String description, Provider provider) {
        this();
        this.code = code;
        this.reference = reference;
        this.description = description;
        this.provider = provider;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String longDescription() {
        return this.reference + " (" + this.stock + ")" + this.wholesalePrice + "€";
    }

    @Override
    public String toString() {
        String providerId = "null";
        if (provider != null) {
            providerId = "" + provider.getId();
        }
        return "Article [" + super.toString() + "stock=" + stock + ", wholesalePrice=" + wholesalePrice + ", providerId=" + providerId
                + "]";
    }

}
