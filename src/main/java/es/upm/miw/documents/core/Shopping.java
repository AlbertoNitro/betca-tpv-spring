package es.upm.miw.documents.core;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Shopping {

    private int amount;

    private BigDecimal discount;

    @DBRef
    private Article article;

    private String description;

    private BigDecimal retailPrice;

    private ShoppingState shoppingState;

    public Shopping() {
    }

    public Shopping(int amount, BigDecimal discount, Article article) {
        this.amount = amount;
        this.discount = discount;
        this.article = article;
        this.description = article.getDescription();
        this.retailPrice = article.getRetailPrice();
        this.shoppingState = ShoppingState.COMMITTED;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Article getProduct() {
        return article;
    }

    public void setProduct(Article article) {
        this.article = article;
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

    public ShoppingState getShoppingState() {
        return shoppingState;
    }

    public void setShoppingState(ShoppingState shoppingState) {
        this.shoppingState = shoppingState;
    }

    public BigDecimal getShoppingTotal() {
        return this.getShoppingSubtotal().multiply(this.discount);
    }

    public BigDecimal getShoppingSubtotal() {
        return retailPrice.multiply(new BigDecimal(amount));
    }

    @Override
    public String toString() {
        return "Shopping [amount=" + amount + ", discount=" + discount + ", article=" + article + ", description=" + description
                + ", retailPrice=" + retailPrice + ", shoppingState=" + shoppingState + "]";
    }

}
