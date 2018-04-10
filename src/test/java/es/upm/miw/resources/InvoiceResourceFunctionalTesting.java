package es.upm.miw.resources;

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

import es.upm.miw.dtos.InvoiceCreationInputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class InvoiceResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testCreateInvoice() {
        InvoiceCreationInputDto invoiceCreationInputDto = new InvoiceCreationInputDto("666666004","201801123");
         restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(InvoiceResource.INVOICES).body(invoiceCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateInvoiceTicketNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        InvoiceCreationInputDto invoiceCreationInputDto = new InvoiceCreationInputDto("666666004",null);
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(InvoiceResource.INVOICES).body(invoiceCreationInputDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateInvoiceMobileNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        InvoiceCreationInputDto invoiceCreationInputDto = new InvoiceCreationInputDto(null,"20180112-3");
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(InvoiceResource.INVOICES).body(invoiceCreationInputDto)
                .clazz(byte[].class).post().build();
   }

 }
