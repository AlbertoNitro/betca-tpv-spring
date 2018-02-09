package es.upm.miw.dtos.input;

import java.math.BigDecimal;
import java.util.List;

import es.upm.miw.dtos.ShoppingDto;

public class TicketDto {

    private Long userMobile;

    private List<ShoppingDto> shoppingDtoList;

    private BigDecimal cashDeposited;

    public TicketDto() {
        // Empty for JSON
    }

    public TicketDto(Long userMobile, List<ShoppingDto> shoppingDtoList, BigDecimal cashDeposited) {
        this.userMobile = userMobile;
        this.shoppingDtoList = shoppingDtoList;
        this.cashDeposited = cashDeposited;
    }

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    public List<ShoppingDto> getShoppingDtoList() {
        return shoppingDtoList;
    }

    public void setShoppingDtoList(List<ShoppingDto> shoppingDtoList) {
        this.shoppingDtoList = shoppingDtoList;
    }

    public BigDecimal getCashDeposited() {
        return cashDeposited;
    }

    public void setCashDeposited(BigDecimal cashDeposited) {
        this.cashDeposited = cashDeposited;
    }

    @Override
    public String toString() {
        return "TicketDto [userMobile=" + userMobile + ", shoppingDtoList=" + shoppingDtoList + ", cashDeposited=" + cashDeposited + "]";
    }

}
