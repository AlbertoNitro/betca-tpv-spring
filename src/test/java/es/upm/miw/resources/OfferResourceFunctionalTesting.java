package es.upm.miw.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Offer;
import es.upm.miw.dtos.OfferInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class OfferResourceFunctionalTesting {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private RestService restService;
	
	private SimpleDateFormat factoryDate=  new SimpleDateFormat(Offer.DATE_FORMAT);
	
    @Test
    public void testCreateOffer() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("TEST-A", new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testCreateOfferWithoutCodeException() throws ParseException {
    	this.thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    	OfferInputDto offerInputDto = new OfferInputDto(null, new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testCreateOfferWithoutPercentageException() throws ParseException {
    	this.thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testCreateOfferWithoutExpirationDateException() {
    	thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testCreateOfferWithExpirationDateThatIsExpiredException() {
    	thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testPutOffer() {
    	
    }
    
    @Test
    public void testPutOfferWithThatNotExistException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
    }
    
    @Test
    public void testReadOffer() {

    }
}
