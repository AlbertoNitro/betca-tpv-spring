package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Offer;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OfferRepositoryIT {
    @Autowired
    private OfferRepository offerRepository;
    
    @Test
    public void findOrderByCodeTest() {
    	Offer offer = this.offerRepository.findByCode("AAA");
        assertEquals("AAA", offer.getCode());
        System.out.println(offer.toString());
        assertEquals(new Float(0.25), this.offerRepository.findByCode("AAA").getPercentage());
        
    }
    
    @Test
    public void findAllOrderTest() {
        List<Offer> offerList = this.offerRepository.findAll();
        assertEquals(true,offerList.size()>0);
    }
}