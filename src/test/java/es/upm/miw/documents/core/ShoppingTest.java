package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ShoppingTest {

    @Test
    public void testGetShoppingTotal() {
        Article article = new Article();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping shopping = new Shopping(2, new BigDecimal("20"), article);
        assertEquals(160.16,shopping.getShoppingTotal().doubleValue(),10-10);
    }

}
