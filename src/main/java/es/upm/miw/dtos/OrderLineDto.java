package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;

import es.upm.miw.documents.core.OrderLine;

public class OrderLineDto {

    @NotNull
    private String articleId;
    
    private String articleDescription;
    
    private Integer stock;

    @NotNull
    private Integer requiredAmount;

    private Integer finalAmount;

    public OrderLineDto() {
    }

    public OrderLineDto(OrderLine orderLine) {
        this.articleId = orderLine.getArticle().getCode();
        this.articleDescription=orderLine.getArticle().getDescription();
        this.stock = orderLine.getArticle().getStock();
        this.requiredAmount = orderLine.getRequiredAmount();
        this.finalAmount = orderLine.getFinalAmount();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(Integer requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Override
    public String toString() {
        return "OrderLineDto [articleId=" + articleId + ", articleDescription=" + articleDescription + ", stock=" + stock
                + ", requiredAmount=" + requiredAmount + ", finalAmount=" + finalAmount + "]";
    }

}
