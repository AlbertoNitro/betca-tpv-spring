package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.OfferInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OfferControllerIT {

    @Autowired
    private OfferController offerController;

    @Test
    public void testCodeRepeatedFalse() throws ParseException {
    	Date expiration = (new SimpleDateFormat("dd-MM-yyyy")).parse("01-12-2018");
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33), expiration);
        assertFalse(offerController.codeRepeated(offerInputDto));
    }
    
    @Test
    public void testCodeRepeatedTrue() throws ParseException {
    	Date expiration = (new SimpleDateFormat("dd-MM-yyyy")).parse("01-12-2018");
    	OfferInputDto offerInputDto = new OfferInputDto("AAA", new Float(0.33), expiration);
        assertTrue(offerController.codeRepeated(offerInputDto));
    }
    
    @Test
    public void testIsExpirationTrue() throws ParseException {
    	Date expiration = (new SimpleDateFormat("dd-MM-yyyy")).parse("01-12-2018");
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33), expiration);
        assertFalse(offerController.isExpiration(offerInputDto));
    }   
    
    @Test
    public void testIsExpirationFalse() throws ParseException {
    	Date expiration = (new SimpleDateFormat("dd-MM-yyyy")).parse("01-01-2018");
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33), expiration);
        assertTrue(offerController.codeRepeated(offerInputDto));
    }   
}
