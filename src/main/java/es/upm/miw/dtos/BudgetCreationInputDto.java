package es.upm.miw.dtos;

import java.util.List;

import es.upm.miw.dtos.validations.ListNotEmpty;

public class BudgetCreationInputDto {

    @ListNotEmpty
    private List<ShoppingDto> shoppingCart;

    public BudgetCreationInputDto() {
        // Empty for framework
    }

    public BudgetCreationInputDto(List<ShoppingDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<ShoppingDto> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "BudgetCreationInputDto [shoppingCart=" + shoppingCart + "]";
    }

}
