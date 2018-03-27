package es.upm.miw.dtos;

import java.math.BigDecimal;

import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;

public class ShoppingOutputDto {

    private String description;

    private BigDecimal retailPrice;

    private int amount;

    private BigDecimal discount;

    private boolean committed;

    public ShoppingOutputDto() {
        // Empty for framework
    }

    public ShoppingOutputDto(Shopping shopping) {
        this.description = shopping.getDescription();
        this.retailPrice = shopping.getRetailPrice();
        this.amount = shopping.getAmount();
        this.discount = shopping.getDiscount();
        if (shopping.getShoppingState().equals(ShoppingState.COMMITTED)) {
            this.committed = true;
        } else {
            this.committed = false;
        }
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

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + amount;
        result = (prime * result) + (committed ? 1231 : 1237);
        result = (prime * result) + ((description == null) ? 0 : description.hashCode());
        result = (prime * result) + ((discount == null) ? 0 : discount.hashCode());
        result = (prime * result) + ((retailPrice == null) ? 0 : retailPrice.hashCode());
        return result;
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
        ShoppingOutputDto other = (ShoppingOutputDto) obj;
        if (amount != other.amount) {
            return false;
        }
        if (committed != other.committed) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (discount == null) {
            if (other.discount != null) {
                return false;
            }
        } else if (!discount.equals(other.discount)) {
            return false;
        }
        if (retailPrice == null) {
            if (other.retailPrice != null) {
                return false;
            }
        } else if (!retailPrice.equals(other.retailPrice)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShoppingOutputDto [description=" + description + ", retailPrice=" + retailPrice + ", amount=" + amount + ", discount="
                + discount + ", committed=" + committed + "]";
    }

}
