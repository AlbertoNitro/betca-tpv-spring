package es.upm.miw.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.dtos.validations.ListNotEmpty;

@JsonInclude(Include.NON_NULL)
public class BudgetDto {

    private String id;

    @ListNotEmpty
    private List<ShoppingDto> shoppingCart;

    public BudgetDto() {
        // Empty for framework
    }

    public BudgetDto(List<ShoppingDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public BudgetDto(String id, List<ShoppingDto> shoppingCart) {
        this.id = id;
        this.shoppingCart = shoppingCart;
    }

    public String getId() {
        return id;
    }

    public List<ShoppingDto> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "BudgetDto [id=" + id + ", shoppingCart=" + shoppingCart + "]";
    }

}
