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
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
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
            articles.add(new Article(new Barcode().generateEan13code(i), "description " + i, null));
        }
        pdfService.generateLabels24(articles);
    }

    @Test
    public void testGenerateTicket() {
        Article article = new Article("23581","Descripción articulo", null);
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
        Article article = new Article("23581","Descripción articulo", null);
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(3, new BigDecimal("40"), article);
        shoppingList[2] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), article);
        shoppingList[4] = new Shopping(2, new BigDecimal("85"), article);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), article);
        Budget budget = new Budget(shoppingList);
        budget.setId("a3b2");
        pdfService.generateBudget(budget);
    }

    @Test
    public void testGenerateVoucher() {
        Voucher voucher = new Voucher(new BigDecimal(55));
        voucher.setId("a3b2");
        pdfService.generateVoucher(voucher);
    }
}
