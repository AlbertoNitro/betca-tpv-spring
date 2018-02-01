package es.upm.miw.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.utils.Barcode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class PdfServiceIT {

    @Autowired
    private PdfService pdfService;

    @Test
    public void testgenerateLabels24() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            articles.add(new Article(new Barcode().generateEan13code(i), "", "description " + i, null, null));
        }
        pdfService.generateLabels24(articles);
    }

}
