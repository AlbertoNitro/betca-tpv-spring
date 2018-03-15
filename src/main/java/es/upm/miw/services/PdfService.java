package es.upm.miw.services;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Ticket;

@Service
public class PdfService {

    private static final float[] TABLE_COLUMNS_SIZES = {20, 85, 20, 30, 40, 15};

    private static final String[] TABLE_COLUMNS_HEADERS = {" ", "Desc.", "Ud.", "Dto.", "€", "E."};

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public Optional<byte[]> generateLabels24(List<Article> articles) {
        final String path = "/labels/label24-" + new SimpleDateFormat("yyyyMMdd-HH-mm").format(new Date().getTime());
        PdfTag24Builder pdf = new PdfTag24Builder(path);

        for (Article article : articles) {
            pdf.addTag24(article.getDescription(), article.getCode());
        }
        return pdf.build();
    }

    public Optional<byte[]> generateTicket(Ticket ticket) {
        final String path = "/tickets/ticket-" + ticket.getId();
        PdfTicketBuilder pdf = new PdfTicketBuilder(path);
        pdf.addImage("logo-upm.png");
        pdf.paragraphEmphasized("Master en Ingeniería Web. BETCA");
        pdf.paragraphEmphasized("Tfno: +(34) 913366000").paragraph("NIF: Q2818015F").paragraph("Calle Alan Turing s/n, 28031 Madrid");
        pdf.line().paragraphEmphasized("TICKET");
        pdf.barCode(ticket.getId()).line();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(ticket.getCreationDate()));
        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES).tableColumnsHeader(TABLE_COLUMNS_HEADERS);
        for (int i = 0; i < ticket.getShoppingList().length; i++) {
            Shopping shopping = ticket.getShoppingList()[i];
            String state = "";
            if (shopping.getShoppingState() != ShoppingState.COMMITTED) {
                state = "N";
            }
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%",
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€", state);
        }
        pdf.tableColspanRight("TOTAL: " + ticket.getTicketTotal().setScale(2, RoundingMode.HALF_UP) + "€");

        pdf.line().paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        pdf.paragraphEmphasized("Gracias por su compra");
        pdf.qrCode(ticket.getReference());

        return pdf.build();
    }

    public Optional<byte[]> generateBudget(Budget budget) {
        final String path = "/budgets/budget-" + budget.getId();
        PdfTicketBuilder pdf = new PdfTicketBuilder(path);
        pdf.addImage("logo-upm.png");
        pdf.paragraphEmphasized("Master en Ingeniería Web. BETCA");
        pdf.paragraphEmphasized("Tfno: +(34) 913366000").paragraph("NIF: Q2818015F").paragraph("Calle Alan Turing s/n, 28031 Madrid");
        pdf.line().paragraphEmphasized("PRESUPUESTO");
        pdf.barCode("PR" + budget.getId()).line();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(budget.getCreationDate()));

        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES).tableColumnsHeader(TABLE_COLUMNS_HEADERS);
        for (int i = 0; i < budget.getShoppingList().length; i++) {
            Shopping shopping = budget.getShoppingList()[i];
            String state = "";
            if (shopping.getShoppingState() != ShoppingState.COMMITTED) {
                state = "N";
            }
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%",
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€", state);
        }
        pdf.tableColspanRight("TOTAL: " + budget.getBudgetTotal().setScale(2, RoundingMode.HALF_UP) + "€");

        pdf.line().paragraph("Este presupuesto es válido durante 15 días. A partir de esa fecha los precios pueden variar.");
        return pdf.build();
    }
    
    public Optional<byte[]> generateInvioce(){
        return null;
    }

}
