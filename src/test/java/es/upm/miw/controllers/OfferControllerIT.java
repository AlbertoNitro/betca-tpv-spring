package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OfferControllerIT {

    @Autowired
    private OfferController offerController;

    @Test
    public void testCodeRepeatedFalse() {
        assertFalse(true);
    }
    
    @Test
    public void testCodeRepeatedTrue() {
        assertTrue(false);
    }
    
    @Test
    public void testIsExpirationTrue() {
        assertFalse(true);
    }   
    
    @Test
    public void testIsExpirationFalse() {
        assertTrue(false);
    }   
}
