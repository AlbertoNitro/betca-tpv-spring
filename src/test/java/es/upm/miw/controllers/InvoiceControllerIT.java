package es.upm.miw.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import es.upm.miw.dtos.InvoiceCreationInputDto;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class InvoiceControllerIT {

    @Autowired
    private InvoiceController invoiceController;

    @Test
    public void testCreateInvioce() {
        assertTrue(this.invoiceController.createInvoice(new InvoiceCreationInputDto("666666004","201801121")).isPresent());
    }

}
