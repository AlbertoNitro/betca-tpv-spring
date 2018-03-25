package es.upm.miw.dtos;

import java.math.BigDecimal;

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

    public ArticleDto() {
        // Empty for framework
    }

    public ArticleDto(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public ArticleDto(String code, String reference, String description, BigDecimal retailPrice, Integer stock) {
        super();
        this.code = code;
        this.reference = reference;
        this.description = description;
        this.retailPrice = retailPrice;
        this.stock = stock;
    }

    public ArticleDto(Article article, String provider) {
        this(article.getCode(), article.getReference(), article.getDescription(), article.getRetailPrice(), article.getStock());
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

    @Override
    public String toString() {
        return "ArticleDto [code=" + code + ", reference=" + reference + ", description=" + description + ", retailPrice=" + retailPrice
                + ", stock=" + stock + ", provider=" + provider + "]";
    }

}
