package es.upm.miw.dtos.input;

import java.math.BigDecimal;
import java.util.List;

import es.upm.miw.dtos.ShoppingDto;

public class TicketCreationDto {
    private Long userMobile;

    private BigDecimal cash;

    private BigDecimal card;

    private BigDecimal voucher;

    private List<ShoppingDto> shoppingCart;

    public TicketCreationDto() {
        // Empty for JSON
    }

    public TicketCreationDto(Long userMobile, BigDecimal cash, BigDecimal card, BigDecimal voucher, List<ShoppingDto> shoppingCart) {
        this.userMobile = userMobile;
        this.cash = cash;
        this.card = card;
        this.voucher = voucher;
        this.shoppingCart = shoppingCart;
    }


    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getCard() {
        return card;
    }

    public void setCard(BigDecimal card) {
        this.card = card;
    }

    public BigDecimal getVoucher() {
        return voucher;
    }

    public void setVoucher(BigDecimal voucher) {
        this.voucher = voucher;
    }

    public List<ShoppingDto> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "TicketCreationDto [userMobile=" + userMobile + ", cash=" + cash + ", card=" + card + ", voucher=" + voucher + ", shoppingCart="
                + shoppingCart + "]";
    }

}
