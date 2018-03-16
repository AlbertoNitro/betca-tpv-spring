package es.upm.miw.documents.core;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class BudgetTest {

    @Test
    public void testGetShoppingList() {
        Article article = new Article();
        article.setRetailPrice(new BigDecimal("50"));
        Shopping[] shoppingList = new Shopping[1];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        Budget budget = new Budget(shoppingList);
        assertEquals(1, budget.getShoppingList().length);
    }
    
    @Test
    public void testGetBudgetTotal() {
        Article article = new Article();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[2];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        Budget budget = new Budget();
        budget.setShoppingList(shoppingList);
        assertEquals(340.34, budget.getBudgetTotal().doubleValue(), 10 - 10);
    }
}
