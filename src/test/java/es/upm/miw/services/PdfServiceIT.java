package es.upm.miw.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Tax;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.utils.Barcode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class PdfServiceIT {

    @Autowired
    private PdfService pdfService;

    @Test
    public void testGenerateLabels24() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            articles.add(new Article(new Barcode().generateEan13code(i), "", "description " + i, null, null));
        }
        pdfService.generateLabels24(articles);
    }

    @Test
    public void testGenerateTicket() {
        Article article = new Article("23581", "referencia", "Descripción articulo", null, null);
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        shoppingList[2] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), article);
        shoppingList[4] = new Shopping(2, new BigDecimal("85"), article);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), article);
        Ticket ticket = new Ticket(1, new BigDecimal("20"), shoppingList, null);
        pdfService.generateTicket(ticket);
    }

    @Test
    public void testGenerateBudget() {
        Article article = new Article("23581", "referencia", "Descripción articulo", null, null);
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        shoppingList[2] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), article);
        shoppingList[4] = new Shopping(2, new BigDecimal("85"), article);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), article);
        Budget budget = new Budget(shoppingList);
        pdfService.generateBudget(budget);
    }
    
    @Test
    public void testGenerateVoucher() {
        Voucher voucher = new Voucher( new BigDecimal( 55 ) );
        pdfService.generateVoucher(voucher);
    }
    
    @Test
    public void testGenerateInvoice() {
        User user = new User("121212121", "Juan", "juan", "1104456987", "Direccion", "");
        Article articleTaxFree = new Article("23581", "referencia1", "Descripción articulo", null, Tax.FREE);
        articleTaxFree.setRetailPrice(new BigDecimal("100.10"));
        Article articleTaxSuperReduced = new Article("23582", "referencia2", "Descripción articulo", null, Tax.SUPER_REDUCED);
        articleTaxSuperReduced.setRetailPrice(new BigDecimal("100.10"));
        Article articleTaxReduced = new Article("23583", "referencia3", "Descripción articulo", null, Tax.REDUCED);
        articleTaxReduced.setRetailPrice(new BigDecimal("100.10"));
        Article articleTaxGeneral = new Article("23584", "referencia4", "Descripción articulo", null, Tax.GENERAL);
        articleTaxGeneral.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), articleTaxFree);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), articleTaxFree);
        shoppingList[2] = new Shopping(2, new BigDecimal("20"), articleTaxSuperReduced);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), articleTaxReduced);
        shoppingList[4] = new Shopping(2, new BigDecimal("85"), articleTaxGeneral);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), articleTaxGeneral);

        Ticket ticket = new Ticket(1, new BigDecimal("20"), shoppingList, user);
        Invoice invoice =  new Invoice(1, ticket);
        pdfService.generateInvioce(invoice); 
    }
}
