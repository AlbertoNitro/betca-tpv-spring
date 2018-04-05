package es.upm.miw.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
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
	
    @After
    public void after() {
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).path(OfferResource.OFFER_CODE).expand("TEST").delete().build();
    }
	
    @Test
    public void testCreateOffer() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testCreateOfferRepeatedException() throws ParseException {
    	this.thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
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
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", null, factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testCreateOfferWithoutExpirationDateException() {
    	this.thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), null);
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testCreateOfferWithExpirationDateThatIsExpiredException() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-01-01"));
    	this.thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    }
    
    @Test
    public void testReadOffer() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
        String json = this.restService.loginAdmin().restBuilder(new RestBuilder<String>()).clazz(String.class).path(OfferResource.OFFERS).path(OfferResource.OFFER_CODE).expand("TEST").get().build();
        System.out.println("------------>"+json);
    }
    
    @Test
    public void testReadOfferThatNotExistException() throws ParseException {
    	this.thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).path(OfferResource.OFFER_CODE).expand("TEST-NOT-FOUND").get().build();
    }
    
    @Test
    public void testPutOffer() throws ParseException {
    	OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-12-01"));
    	this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).body(offerInputDto).post().build();
    	offerInputDto.setPercentage(new Float(0.7));
        this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).path(OfferResource.OFFER_CODE).expand("TEST").body(offerInputDto).put().build();
    }
    
    @Test
    public void testPutOfferThatNotExistException() throws ParseException {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        OfferInputDto offerInputDto = new OfferInputDto("TEST", new Float(0.5), factoryDate.parse("2018-12-01"));
        this.restService.loginAdmin().restBuilder().path(OfferResource.OFFERS).path(OfferResource.OFFER_CODE).expand("TEST").body(offerInputDto).put().build();
    }
}
