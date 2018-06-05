package es.upm.miw.businessServices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessServices.Barcode;
import es.upm.miw.businessServices.PdfService;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.exceptions.PdfException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class PdfServiceIT {

    @Autowired
    private PdfService pdfService;

    @Test
    public void testGenerateLabels24() throws PdfException {
        List<Article> articles = new ArrayList<>();
        for (long i = 0; i < 48; i++) {
            articles.add(Article.builder().code(new Barcode().generateEan13code(840000005000L + i)).description("d" + i).reference("r" + i)
                    .build());
        }
        pdfService.generateLabels24(articles);
    }

    @Test
    public void testGenerateLabels65() throws PdfException {
        List<Article> articles = new ArrayList<>();
        for (long i = 0; i < 65; i++) {
            articles.add(Article.builder().code(new Barcode().generateEan13code(840000005000L + i)).description("d" + i).reference("reference large - " + i)
                    .build());
        }
        pdfService.generateLabels65(articles);
    }

    @Test
    public void testGenerateTicket() throws PdfException {
        Article article = Article.builder().code("8400000000017").description("Descripción articulo").build();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(12, new BigDecimal("00"), article);
        shoppingList[2] = new Shopping(2, new BigDecimal("00"), article);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), article);
        shoppingList[4] = new Shopping(2, new BigDecimal("00"), article);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), article);
        Ticket ticket = new Ticket(1, new BigDecimal("20"), shoppingList, null);
        pdfService.generateTicket(ticket);
    }

    @Test
    public void testGenerateBudget() throws PdfException {
        Article article = Article.builder().code("8400000000017").description("Descripción articulo").build();
        article.setRetailPrice(new BigDecimal("100.10"));
        Shopping[] shoppingList = new Shopping[6];
        shoppingList[0] = new Shopping(2, new BigDecimal("20"), article);
        shoppingList[1] = new Shopping(12, new BigDecimal("00"), article);
        shoppingList[2] = new Shopping(2, new BigDecimal("00"), article);
        shoppingList[3] = new Shopping(3, new BigDecimal("90"), article);
        shoppingList[4] = new Shopping(2, new BigDecimal("00"), article);
        shoppingList[5] = new Shopping(3, new BigDecimal("50"), article);
        Budget budget = new Budget(shoppingList);
        budget.setId("2jC7A_9B");
        pdfService.generateBudget(budget);
    }

    @Test
    public void testGenerateVoucher() throws PdfException {
        Voucher voucher = new Voucher(new BigDecimal(55));
        voucher.setId("2jC7A_9B");
        pdfService.generateVoucher(voucher);
    }

}
