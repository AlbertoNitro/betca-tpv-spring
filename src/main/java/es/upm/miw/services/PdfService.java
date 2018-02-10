package es.upm.miw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Ticket;

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

    public Optional<byte[]> generateTicket(Ticket ticket) {
        final String path = "/tickets/ticket-"+ticket.getId();

        PdfTicketBuilder pdf = new PdfTicketBuilder(path);
        pdf.paragraphEmphasized("Tfno: +(34) 600600600").paragraph("NIF: 00000000D").paragraph("C/ ... 28031 Madrid");
        return pdf.build();
    }

}
