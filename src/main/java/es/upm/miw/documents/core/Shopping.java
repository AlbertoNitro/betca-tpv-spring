package es.upm.miw.documents.core;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Shopping {

    private Integer amount;

    private BigDecimal discount;

    private String description;

    private BigDecimal retailPrice;

    private ShoppingState shoppingState;

    @DBRef
    private Article article;

    public Shopping() {
    }

    public Shopping(Integer amount, BigDecimal discount, Article article) {
        this.amount = amount;
        this.discount = discount;
        this.article = article;
        this.description = article.getDescription();
        this.retailPrice = article.getRetailPrice();
        this.shoppingState = ShoppingState.COMMITTED;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
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
        return retailPrice.multiply(new BigDecimal(amount))
                .multiply(new BigDecimal("1").subtract(this.discount.divide(new BigDecimal("100"))));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((article == null) ? 0 : article.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((discount == null) ? 0 : discount.hashCode());
        result = prime * result + ((retailPrice == null) ? 0 : retailPrice.hashCode());
        result = prime * result + ((shoppingState == null) ? 0 : shoppingState.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Shopping other = (Shopping) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (article == null) {
            if (other.article != null)
                return false;
        } else if (!article.equals(other.article))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (discount == null) {
            if (other.discount != null)
                return false;
        } else if (!discount.equals(other.discount))
            return false;
        if (retailPrice == null) {
            if (other.retailPrice != null)
                return false;
        } else if (!retailPrice.equals(other.retailPrice))
            return false;
        if (shoppingState != other.shoppingState)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Shopping [amount=" + amount + ", discount=" + discount + ", article=" + article.getReference() + ", description="
                + description + ", retailPrice=" + retailPrice + ", shoppingState=" + shoppingState + "]";
    }

}
