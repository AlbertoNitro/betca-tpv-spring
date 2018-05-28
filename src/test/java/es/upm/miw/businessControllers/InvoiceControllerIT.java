package es.upm.miw.businessControllers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessControllers.InvoiceController;
import es.upm.miw.dtos.InvoiceCreationInputDto;
import es.upm.miw.exceptions.PdfException;
import es.upm.miw.exceptions.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class InvoiceControllerIT {

    @Autowired
    private InvoiceController invoiceController;

    @Test
    public void testCreateInvioce() throws NotFoundException, PdfException {
        assertTrue(this.invoiceController.createInvoice(new InvoiceCreationInputDto("666666004", "201801121")).length > 0);
    }

}
