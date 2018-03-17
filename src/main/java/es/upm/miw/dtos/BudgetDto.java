package es.upm.miw.dtos;

import java.util.List;

import es.upm.miw.dtos.validations.ListNotEmpty;

public class BudgetDto {

    private String id;
    
    @ListNotEmpty
    private List<ShoppingDto> shoppingCart;

    public BudgetDto() {
        // Empty for framework
    }
    
    

    public BudgetDto(String id) {
        this.id = id;
    }



    public String getId() {
        return id;
    }



    public BudgetDto(List<ShoppingDto> shoppingCart) {
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
