package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Article;

@JsonInclude(Include.NON_NULL)
public class ArticleDto {

    private String code;

    private String reference;

    private String description;

    private BigDecimal retailPrice;

    private Integer stock;

    private String provider;

    private Boolean discontinued;

    private Date registrationDate;

    public ArticleDto() {
        // Empty for framework
    }

    public ArticleDto(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ArticleDto(String code, String description, String reference, BigDecimal retailPrice, Integer stock) {
        this(code, description);
        this.reference = reference;
        this.retailPrice = retailPrice;
        this.stock = stock;
    }

    public ArticleDto(Article article, String provider) {
        this(article.getCode(), article.getDescription(), article.getReference(), article.getRetailPrice(), article.getStock());
        this.setDiscontinued(article.getDiscontinued());
        this.setRegistrationDate(article.getRegistrationDate());
        this.setProvider(provider);
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        String date = "null";
        if (registrationDate != null) {
            date = new SimpleDateFormat("dd-MMM-yyyy").format(registrationDate.getTime());
        }
        return "ArticleDto [code=" + code + ", reference=" + reference + ", description=" + description + ", retailPrice=" + retailPrice
                + ", stock=" + stock + ", provider=" + provider + ", discontinued=" + discontinued + ", registrationDate=" + date + "]";
    }

}
