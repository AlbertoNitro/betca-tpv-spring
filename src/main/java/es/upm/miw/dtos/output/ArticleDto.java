package es.upm.miw.dtos.output;

import java.math.BigDecimal;

public class ArticleDto {

    private String code;

    private String reference;

    private String description;

    private BigDecimal retailPrice;

    private BigDecimal tax;

    private int stock;

    public ArticleDto() {
    }

    public ArticleDto(String code, String reference, String description, BigDecimal retailPrice, BigDecimal tax, int stock) {
        this.code = code;
        this.reference = reference;
        this.description = description;
        this.retailPrice = retailPrice;
        this.tax = tax;
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

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ArticleDto [code=" + code + ", reference=" + reference + ", description=" + description + ", retailPrice=" + retailPrice
                + ", tax=" + tax + ", stock=" + stock + "]";
    }
    
}
