package es.upm.miw.controllers;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.TicketDto;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticlesTrackingControllerIT {
	
    @Autowired
    private ArticlesTrackingController articlesTrackingController;

	@Test
    public void testGetTicketNotCommited() {
      
        Optional<TicketDto> pdf1 = this.articlesTrackingController.findTicketNotCommitted("20180112-3");
        assertTrue(!pdf1.isPresent());
    }

}
