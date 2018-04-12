package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class TicketTest {

    @Test
    public void testGetTicketTotal() {
        Article article = new Article();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[2];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        Ticket ticket = new Ticket();
        ticket.setShoppingList(shoppingList);
        assertEquals(340.34,ticket.getTotal().doubleValue(),10-10 );
    }
    
    @Test
    public void testSimpleId() {
        Article article = new Article();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[2];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        Ticket ticket = new Ticket(1,new BigDecimal("20"),shoppingList,null);
        assertEquals(1,ticket.simpleId());
    }

}
