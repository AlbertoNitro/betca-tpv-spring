package es.upm.miw.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.repositories.core.PropertyRepository;
import es.upm.miw.utils.Encrypting;

@Service
public class PdfService {

    public static String LOGO = "miw.company.logo";

    public static String NAME = "miw.company.name";

    public static String NIF = "miw.company.nif";

    public static String PHONE = "miw.company.phone";

    public static String ADDRESS = "miw.company.address";

    public static String EMAIL = "miw.company.email";

    public static String WEB = "miw.company.web";

    @Autowired
    private PropertyRepository propertyRepository;

    private String logo;

    private String name;

    private String nif;

    private String phone;

    private String address;

    private String email;

    private String web;

    private static final float[] TABLE_COLUMNS_SIZES = {20, 85, 20, 30, 40, 15};

    private static final float[] TABLE_COLUMNS_SIZES_BUDGETS = {20, 85, 20, 40, 40};

    private static final String[] TABLE_COLUMNS_HEADERS = {" ", "Desc.", "Ud.", "Dto.", "€", "E."};

    private static final String[] TABLE_COLUMNS_HEADERS_BUDGETS = {" ", "Desc.", "Ud.", "Dto.", "€"};

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @PostConstruct
    public void constructor() {
        if (this.propertyRepository.findOne(LOGO) == null) {
            this.logo = "logo-upm.png";
            this.name = "aster en Ingenieríeda Web. BETCA";
            this.nif = "Q2818015F";
            this.phone = "+34 913366000";
            this.address = "Calle Alan Turing s/n, 28031 Madrid";
            this.email = "miw@etsisi.upm.es";
            this.web = "miw.etsisi.upm.es";
        } else {
            this.logo = this.propertyRepository.findOne(LOGO).getValue();
            this.name = this.propertyRepository.findOne(NAME).getValue();
            this.nif = this.propertyRepository.findOne(NIF).getValue();
            this.phone = this.propertyRepository.findOne(PHONE).getValue();
            this.address = this.propertyRepository.findOne(ADDRESS).getValue();
            this.email = this.propertyRepository.findOne(EMAIL).getValue();
            this.web = this.propertyRepository.findOne(WEB).getValue();
        }
    }

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
        final int INCREMENTAL_HEIGHT = 11;
        boolean notCommitted = false;
        PdfTicketBuilder pdf = this.addCompanyDetails(path, INCREMENTAL_HEIGHT + ticket.getShoppingList().length);

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
                notCommitted = true;
            }

            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%",
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€", state);

        }
        this.totalPrice(pdf, ticket.getTicketTotal());
        pdf.line().paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        pdf.paragraphEmphasized("Gracias por usar nuestros servicios.");
        if (notCommitted) {
            pdf.qrCode(ticket.getReference());
        }

        return pdf.build();
    }

    public Optional<byte[]> generateBudget(Budget budget) {
        final String path = "/budgets/budget-" + budget.getId();
        PdfTicketBuilder pdf = this.addCompanyDetails(path, budget.getShoppingList().length + 2);
        pdf.line().paragraphEmphasized("PRESUPUESTO");
        pdf.barCode(new Encrypting().encodeHexInBase64UrlSafe(budget.getId())).line();

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
        pdf.paragraphEmphasized("Gracias por usar nuestros servicios.");

        return pdf.build();
    }

    public Optional<byte[]> generateVoucher(Voucher voucher) {
        final String path = "/vouchers/voucher-" + voucher.getId();
        PdfTicketBuilder pdf = this.addCompanyDetails(path, 2);

        pdf.line().paragraphEmphasized("VALE");
        pdf.barCode(new Encrypting().encodeHexInBase64UrlSafe(voucher.getId())).line();

        pdf.paragraphEmphasized("      Valor: " + voucher.getValue() + " €").line();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(voucher.getCreationDate()));

        pdf.line().paragraph("Periodo de validez: ilimitado.");
        pdf.paragraphEmphasized("Gracias por usar nuestros servicios.");

        return pdf.build();
    }

    private PdfTicketBuilder addCompanyDetails(String path, int lines) {
        PdfTicketBuilder pdf = new PdfTicketBuilder(path, lines);
        pdf.addImage(this.logo).paragraphEmphasized(this.name).paragraphEmphasized("Tfn: " + this.phone);
        pdf.paragraph("NIF: " + this.nif + "   -   " + this.address).paragraph("Web: " + this.web + "   -   Email: " + this.email);
        return pdf;
    }

    private PdfTicketBuilder totalPrice(PdfTicketBuilder pdf, BigDecimal total) {
        pdf.tableColspanRight("TOTAL: " + total.setScale(2, RoundingMode.HALF_UP) + "€");
        return pdf;
    }

    public Optional<byte[]> generateInvioce(Invoice invoice) {
        final int INCREMENTAL_INVOICE_HEIGHT = 11;
        final String path = "/invoices/invoice-" + invoice.getId();

        PdfTicketBuilder pdf = this.addCompanyDetails(path, INCREMENTAL_INVOICE_HEIGHT + invoice.getTicket().getShoppingList().length);
        pdf.line();
        pdf.paragraphEmphasized("Datos Cliente:").paragraph("DNI: " + invoice.getUser().getDni())
                .paragraph("Nombre: " + invoice.getUser().getUsername()).paragraph("Dirección: " + invoice.getUser().getAddress());
        pdf.line();
        pdf.line().paragraphEmphasized("FACTURA N° " + invoice.getId()).barCode(invoice.getId());
        pdf.line();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(invoice.getCreationDated()));
        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES_BUDGETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS_BUDGETS);
        for (int i = 0; i < invoice.getTicket().getShoppingList().length; i++) {
            Shopping shopping = invoice.getTicket().getShoppingList()[i];
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(),
                    shopping.getDiscount().setScale(2, RoundingMode.HALF_UP) + "%",
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€");
        }

        pdf.tableColspanRight("BASE IMPONIBLE: " + invoice.getBaseTax().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("IVA: " + invoice.getTax().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("TOTAL: " + invoice.getTicket().getTicketTotal().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.line();
        pdf.paragraphEmphasized("Gracias por usar nuestros servicios.");

        return pdf.build();
    }

}
