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

    @DBRef(lazy = true)
    private Provider provider;

    public Article() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Article article;

        private Builder() {
            this.article = new Article();
            this.article.registrationDate = new Date();
            this.article.discontinued = false;
            this.article.stock = 0;
            this.article.tax = Tax.GENERAL;
        }

        public Builder code(String code) {
            this.article.code = code;
            return this;
        }

        public Builder reference(String reference) {
            this.article.reference = reference;
            return this;
        }

        public Builder description(String description) {
            this.article.description = description;
            return this;

        }

        public Builder retailPrice(String retailPrice) {
            this.article.retailPrice = new BigDecimal(retailPrice);
            return this;
        }

        public Builder retailPrice(BigDecimal retailPrice) {
            this.article.retailPrice = retailPrice;
            return this;
        }

        public Builder stock(Integer stock) {
            this.article.stock = stock;
            return this;
        }

        public Builder provider(Provider provider) {
            this.article.provider = provider;
            return this;
        }

        public Article build() {
            return this.article;
        }
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

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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
