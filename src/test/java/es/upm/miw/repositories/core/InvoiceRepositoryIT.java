package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class InvoiceRepositoryIT {

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Test
    public void testFindTicket() {
        assertEquals("20180112-1", invoiceRepository.findOne("2018-1").getTicket().getId());
    }

}
