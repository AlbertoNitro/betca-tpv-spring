package es.upm.miw.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Tax;
import es.upm.miw.documents.core.Ticket;

@Service
public class PdfService {

    @Value("${iva.general}")
    private String ivaGeneral;

    @Value("${iva.reduced}")
    private String ivaReduced;

    @Value("${iva.super.reduced}")
    private String ivaSuperReduced;

    @Value("${iva.free}")
    private String ivaFree;

    private static final float[] TABLE_COLUMNS_SIZES = {20, 85, 20, 30, 40, 15};

    private static final float[] TABLE_COLUMNS_SIZES_BUDGETS = {20, 85, 20, 40, 40};

    private static final String[] TABLE_COLUMNS_HEADERS = {" ", "Desc.", "Ud.", "Dto.", "€", "E."};

    private static final String[] TABLE_COLUMNS_HEADERS_BUDGETS = {" ", "Desc.", "Ud.", "Dto.", "€"};

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
        PdfTicketBuilder pdf = this.headerData(path);

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
        this.totalPrice(pdf, ticket.getTicketTotal());
        pdf.line().paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        pdf.paragraphEmphasized("Gracias por su compra");
        pdf.qrCode(ticket.getReference());

        return pdf.build();
    }

    public Optional<byte[]> generateBudget(Budget budget) {
        final String path = "/budgets/budget-" + budget.getId();
        PdfTicketBuilder pdf = this.headerData(path);

        pdf.line().paragraphEmphasized("PRESUPUESTO");
        if(budget.getId()!=null) {
            pdf.barCode(budget.getId()).line();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(budget.getCreationDate()));

        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES_BUDGETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS_BUDGETS);
        for (int i = 0; i < budget.getShoppingList().length; i++) {
            Shopping shopping = budget.getShoppingList()[i];
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%",
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€");
        }
        this.totalPrice(pdf, budget.getBudgetTotal());
        pdf.line().paragraph("Este presupuesto es válido durante 15 días. A partir de esa fecha los precios pueden variar.");

        return pdf.build();
    }

    private PdfTicketBuilder headerData(String path) {
        PdfTicketBuilder pdf = new PdfTicketBuilder(path);
        pdf.addImage("logo-upm.png");
        pdf.paragraphEmphasized("Master en Ingeniería Web. BETCA");
        pdf.paragraphEmphasized("Tfno: +(34) 913366000").paragraph("NIF: Q2818015F").paragraph("Calle Alan Turing s/n, 28031 Madrid");
        return pdf;
    }

    public Optional<byte[]> generateInvioce(Invoice invoice) {

        BigDecimal baseImpobibleTotal = BigDecimal.ZERO;
        BigDecimal ivaTotal = BigDecimal.ZERO;

        final String path = "/invoices/invoice-" + invoice.getId();
        PdfTicketBuilder pdf = this.headerData(path);
        pdf.line();
        pdf.paragraphEmphasized("Datos Cliente:").paragraph("DNI: " + invoice.getTicket().getUser().getDni())
                .paragraph("Nombre: " + invoice.getTicket().getUser().getUsername())
                .paragraph("Dirección: " + invoice.getTicket().getUser().getAddress());
        pdf.line();
        pdf.line().paragraphEmphasized("FACTURA N° " + invoice.getId());
        pdf.line();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(invoice.getCreated()));
        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES).tableColumnsHeader(TABLE_COLUMNS_HEADERS);

        for (int i = 0; i < invoice.getTicket().getShoppingList().length; i++) {
            Shopping shopping = invoice.getTicket().getShoppingList()[i];
            Article article = invoice.getTicket().getShoppingList()[i].getArticle();
            String state = "";
            if (shopping.getShoppingState() != ShoppingState.COMMITTED) {
                state = "N";
            }
            BigDecimal baseImpobible = getTaxBase(shopping.getShoppingTotal(), article.getTax());
            BigDecimal iva = getIva(baseImpobible, article.getTax());
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%", baseImpobible.setScale(2, RoundingMode.HALF_UP) + "€",
                    state);
            baseImpobibleTotal = baseImpobibleTotal.add(baseImpobible);
            ivaTotal = ivaTotal.add(iva);
        }

        pdf.tableColspanRight("BASE IMPONIBLE: " + baseImpobibleTotal.setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("IVA: " + ivaTotal.setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("TOTAL: " + baseImpobibleTotal.add(ivaTotal).setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.line();
        pdf.line().paragraph("Gracias por su compra");

        return pdf.build();
    }

    private BigDecimal getTaxBase(BigDecimal value, Tax tax) {
        switch (tax) {
        case GENERAL:
            return value.divide(new BigDecimal(String.valueOf(ivaGeneral)).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);

        case REDUCED:
            return value.divide(new BigDecimal(String.valueOf(ivaReduced)).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);

        case SUPER_REDUCED:
            return value.divide(new BigDecimal(String.valueOf(ivaSuperReduced)).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);

        case FREE:
            return value.divide(new BigDecimal(String.valueOf(ivaFree)).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);
        default:
            return value;
        }

    }

    private BigDecimal getIva(BigDecimal value, Tax tax) {

        switch (tax) {
        case GENERAL:
            return value.multiply(new BigDecimal(String.valueOf(ivaGeneral)));

        case REDUCED:
            return value.multiply(new BigDecimal(String.valueOf(ivaReduced)));

        case SUPER_REDUCED:
            return value.multiply(new BigDecimal(String.valueOf(ivaSuperReduced)));

        case FREE:
            return value.multiply(new BigDecimal(String.valueOf(ivaFree)));

        default:
            return new BigDecimal(0.0);
        }

    }

    private PdfTicketBuilder totalPrice(PdfTicketBuilder pdf, BigDecimal total) {
        pdf.tableColspanRight("TOTAL: " + total.setScale(2, RoundingMode.HALF_UP) + "€");
        return pdf;
    }

}
