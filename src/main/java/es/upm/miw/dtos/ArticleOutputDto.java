package es.upm.miw.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ArticleOutputDto {

    private String code;

    private String reference;

    private String description;

    private BigDecimal retailPrice;

    private Integer stock;

    public ArticleOutputDto() {
        // Empty for framework
    }

    public ArticleOutputDto(String code, String reference, String description, BigDecimal retailPrice, Integer stock) {
        super();
        this.code = code;
        this.reference = reference;
        this.description = description;
        this.retailPrice = retailPrice;
        this.stock = stock;
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

    @Override
    public String toString() {
        return "ArticleDto [code=" + code + ", reference=" + reference + ", description=" + description + ", retailPrice=" + retailPrice
                + ", stock=" + stock + "]";
    }

}
