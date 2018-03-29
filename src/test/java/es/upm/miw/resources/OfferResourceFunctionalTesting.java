package es.upm.miw.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import es.upm.miw.dtos.OfferInputDto;

public class OfferResourceFunctionalTesting {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private RestService restService;
	
	private OfferInputDto offerInputDto;
	
	@Before
	public void before() throws ParseException {
		SimpleDateFormat dateFactory = new SimpleDateFormat("dd/MM/YYYY");
	    this.offerInputDto = new OfferInputDto("ZZZ", new Float(0.5), dateFactory.parse("01/12/2018"));
	}
	
    @Test
    public void testCreateOffer() {
    	
    }
    
    @Test
    public void testCreateOfferWithoutCodeException() {
    	thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testCreateOfferWithoutPercentageException() {
    	thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testCreateOfferWithoutExpirationDateException() {
    	thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testPutOffer() {
    	
    }
    
    @Test
    public void testPutOfferWithCodeThatNotExistException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
    }
    
    @Test
    public void testReadOffer() {

    }
    
    @Test
    public void testReadOfferWithoutCodeThatNotExistException() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
    }
}
