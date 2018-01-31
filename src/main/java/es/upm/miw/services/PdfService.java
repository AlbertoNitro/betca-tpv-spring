package es.upm.miw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.utils.PdfTag24Builder;

@Service
public class PdfService {

    public Optional<byte[]> generateLabels24(List<Article> articles) {
        final String path = "/labels/label24";

        PdfTag24Builder pdf = new PdfTag24Builder(path);
        for (Article article : articles) {
            pdf.addTag24(article.getDescription(), article.getCode());
        }
        return pdf.build();
    }

}
