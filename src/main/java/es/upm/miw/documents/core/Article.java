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

    private String description;
    
    private BigDecimal retailPrice;

    private String reference;

    private Integer stock;

    private Tax tax;

    private Boolean discontinued;

    @DBRef
    private Provider provider;

    public Article() {
        this.registrationDate = new Date();
        this.discontinued = false;
        this.stock = 0;
        this.tax = Tax.GENERAL;
    }

    public Article(String code, String description, BigDecimal retailPrice) {
        this();
        this.code = code;
        this.description = description;
        this.retailPrice = retailPrice;
    }

    public Article(String code, String description, BigDecimal retailPrice, String reference, Integer stock, Provider provider,
            Boolean discontinued) {
        this(code, description, retailPrice);
        this.reference = reference;
        this.stock = stock;
        this.provider = provider;
        this.discontinued = discontinued;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
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
        return (code.equals(((Article) obj).code));
    }

    @Override
    public String toString() {
        return "Article [code=" + code + ", registrationDate=" + registrationDate + ", reference=" + reference + ", description="
                + description + ", retailPrice=" + retailPrice + ", stock=" + stock + ", discontinued=" + discontinued + ", tax=" + tax
                + ", provider=" + provider + "]";
    }

}
