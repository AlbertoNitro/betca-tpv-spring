package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.List;

import es.upm.miw.dtos.validations.BigDecimalPositive;
import es.upm.miw.dtos.validations.ListNotEmpty;

public class TicketCreationInputDto {
    private String userMobile;

    @BigDecimalPositive
    private BigDecimal cash;

    @BigDecimalPositive
    private BigDecimal card;

    @BigDecimalPositive
    private BigDecimal voucher;

    @ListNotEmpty
    private List<ShoppingDto> shoppingCart;

    public TicketCreationInputDto() {
        // Empty for framework
    }

    public TicketCreationInputDto(BigDecimal cash, BigDecimal card, BigDecimal voucher, List<ShoppingDto> shoppingCart) {
        this.cash = cash;
        this.card = card;
        this.voucher = voucher;
        this.shoppingCart = shoppingCart;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
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
        return "TicketCreationDto [userMobile=" + userMobile + ", cash=" + cash + ", card=" + card + ", voucher=" + voucher
                + ", shoppingCart=" + shoppingCart + "]";
    }

}
