package es.upm.miw.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Offer;
import es.upm.miw.dtos.OfferInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OfferControllerIT {
    @Autowired
    private OfferController offerController;
    
    private SimpleDateFormat factoryDate = new SimpleDateFormat(Offer.DATE_FORMAT);
    
    @Test
    public void testCodeRepeatedTrue() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("AAA", new Float(0.33), factoryDate.parse("2018-12-01"));
        assertTrue(offerController.codeRepeated(offerInputDto));
    }
    
    @Test
    public void testCodeRepeatedFalse() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33),  factoryDate.parse("2018-12-01"));
        assertFalse(offerController.codeRepeated(offerInputDto));
    } 
    
    @Test
    public void testIsExpirationDateValidTrue() throws ParseException { 	
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33), factoryDate.parse("2018-12-01"));
        assertTrue(offerController.isExpirationDateValid(offerInputDto));
    } 
    
    @Test
    public void testIsExpirationDateValidFalse() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("CCC", new Float(0.33), factoryDate.parse("2018-01-01"));
        assertFalse(offerController.isExpirationDateValid(offerInputDto));
    }   
    
  
}
