package es.upm.miw.dtos;

import java.math.BigDecimal;

public class ShoppingDto {

    private String code;

    private String description;

    private BigDecimal retailPrice;

    private int amount;

    private BigDecimal discount;

    private BigDecimal total;

    private boolean committed;

    public ShoppingDto() {
        // Empty for framework
    }

    public ShoppingDto(String code, String description, BigDecimal retailPrice, int amount, BigDecimal discount, BigDecimal total,
            boolean committed) {
        this.code = code;
        this.description = description;
        this.retailPrice = retailPrice;
        this.amount = amount;
        this.discount = discount;
        this.total = total;
        this.committed = committed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        result = prime * result + amount;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + (committed ? 1231 : 1237);
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((discount == null) ? 0 : discount.hashCode());
        result = prime * result + ((retailPrice == null) ? 0 : retailPrice.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
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
        ShoppingDto other = (ShoppingDto) obj;
        if (amount != other.amount)
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (committed != other.committed)
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
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ShoppingDto [code=" + code + ", description=" + description + ", retailPrice=" + retailPrice + ", amount=" + amount
                + ", discount=" + discount + ", total=" + total + ", committed=" + committed + "]";
    }

}
