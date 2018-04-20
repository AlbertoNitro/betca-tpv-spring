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

    private static final String[] TABLE_COLUMNS_HEADERS = {" ", "Desc.", "Ud.", "Dto.%", "€", "E."};

    private static final float[] TABLE_COLUMNS_SIZES_TICKETS = {15, 90, 15, 25, 35, 15};

    private static final String[] TABLE_COLUMNS_HEADERS_BUDGETS = {" ", "Desc.", "Ud.", "Dto.%", "€"};

    private static final float[] TABLE_COLUMNS_SIZES_BUDGETS = {15, 95, 15, 25, 40};

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @PostConstruct
    public void constructor() {
        if (this.propertyRepository.findOne(LOGO) == null) {
            this.logo = "logo-upm.png";
            this.name = "Master en Ingenieríeda Web. BETCA";
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
        final int INCREMENTAL_HEIGHT = 15;
        int notCommitted = 0;
        PdfTicketBuilder pdf = this.addCompanyDetails(path, INCREMENTAL_HEIGHT + ticket.getShoppingList().length).line();

        if (ticket.getDebt().signum() != 1) {
            pdf.paragraphEmphasized("TICKET");
        } else {
            BigDecimal pendiente = ticket.getTotal().subtract(ticket.getTotalCommited()).setScale(2, RoundingMode.HALF_UP);
            pdf.paragraphEmphasized("RESERVA: " + pendiente.subtract(ticket.getDebt()).setScale(2, RoundingMode.HALF_UP) + "€");
            pdf.paragraphEmphasized("Abonado: " + ticket.getTotalCommited().setScale(2, RoundingMode.HALF_UP) + "€");
            pdf.paragraphEmphasized("Pendiente: " + pendiente.setScale(2, RoundingMode.HALF_UP) + "€");
        }
        pdf.barCode(ticket.getId()).line();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(ticket.getCreationDate()));
        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES_TICKETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS);

        for (int i = 0; i < ticket.getShoppingList().length; i++) {
            Shopping shopping = ticket.getShoppingList()[i];
            String state = "";
            if (shopping.getShoppingState() != ShoppingState.COMMITTED && shopping.getAmount() > 0) {
                state = "N";
                notCommitted++;
            }
            String discount = "";
            if ((shopping.getDiscount().doubleValue() > 0.009) && !shopping.getArticle().getCode().equals("1")) {
                discount = "" + shopping.getDiscount().setScale(2, RoundingMode.HALF_UP);
            }
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(), discount,
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€", state);
        }

        this.totalPrice(pdf, ticket.getTotal());
        pdf.paragraph(ticket.getNote());
        pdf.line().paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        if (notCommitted > 0) {
            pdf.paragraphEmphasized("Artículos pendientes de entrega: " + notCommitted);
            if (ticket.getUser() != null) {
                pdf.paragraph("Teléfono de contacto: " + ticket.getUser().getMobile() + " - " + ticket.getUser().getUsername().substring(0,
                        (ticket.getUser().getUsername().length() > 10) ? 10 : ticket.getUser().getUsername().length()));
            }
            // pdf.qrCode(ticket.getReference());
        }
        pdf.paragraphEmphasized("Gracias por su visita").paragraphEmphasized(" ").line();
        return pdf.build();
    }

    public Optional<byte[]> generateBudget(Budget budget) {
        final String path = "/budgets/budget-" + budget.getId();
        final int INCREMENTAL_HEIGHT = 10;
        PdfTicketBuilder pdf = this.addCompanyDetails(path, INCREMENTAL_HEIGHT + budget.getShoppingList().length);
        pdf.line().paragraphEmphasized("PRESUPUESTO");
        pdf.barCode(budget.getId()).line();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(budget.getCreationDate()));

        pdf.tableColumnsSizes(TABLE_COLUMNS_SIZES_BUDGETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS_BUDGETS);
        for (int i = 0; i < budget.getShoppingList().length; i++) {
            Shopping shopping = budget.getShoppingList()[i];
            String discount = "";
            if ((shopping.getDiscount().doubleValue() > 0.009) && !shopping.getArticle().getCode().equals("1")) {
                discount = "" + shopping.getDiscount().setScale(2, RoundingMode.HALF_UP);
            }

            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(), discount,
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€");

        }
        this.totalPrice(pdf, budget.getBudgetTotal());
        pdf.line().paragraph("Este presupuesto es válido durante 15 días. A partir de esa fecha los precios pueden variar.");
        pdf.paragraphEmphasized("Gracias por su visita").paragraphEmphasized(" ").line();
        return pdf.build();
    }

    public Optional<byte[]> generateVoucher(Voucher voucher) {
        final String path = "/vouchers/voucher-" + voucher.getId();
        PdfTicketBuilder pdf = this.addCompanyDetails(path, 6);

        pdf.line().paragraphEmphasized("VALE");
        pdf.barCode(voucher.getId()).line();

        pdf.paragraphEmphasized("      Valor: " + voucher.getValue().setScale(2, RoundingMode.HALF_UP) + " €").line();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(voucher.getCreationDate()));

        pdf.line().paragraph("Periodo de validez: 2 años.");
        pdf.paragraphEmphasized("Gracias por su visita").paragraphEmphasized(" ").line();
        return pdf.build();
    }

    private PdfTicketBuilder addCompanyDetails(String path, int lines) {
        PdfTicketBuilder pdf = new PdfTicketBuilder(path, lines);
        pdf.addImage(this.logo).paragraphEmphasized(this.name).paragraphEmphasized("Tfn: " + this.phone);
        pdf.paragraph("NIF: " + this.nif + "   -   " + this.address).paragraph("Email: " + this.email);
        if (!this.web.isEmpty()) {
            pdf.paragraph("Web: " + this.web);
        }
        return pdf;
    }

    private PdfTicketBuilder totalPrice(PdfTicketBuilder pdf, BigDecimal total) {
        pdf.tableColspanRight("TOTAL: " + total.setScale(2, RoundingMode.HALF_UP) + "€");
        return pdf;
    }

    public Optional<byte[]> generateInvioce(Invoice invoice) {
        final int INCREMENTAL_INVOICE_HEIGHT = 16;
        final String path = "/invoices/invoice-" + invoice.getId();

        PdfTicketBuilder pdf = new PdfTicketBuilder(path, INCREMENTAL_INVOICE_HEIGHT);
        pdf.addImage(this.logo).paragraphEmphasized(this.name).paragraphEmphasized("Tfn: " + this.phone);
        pdf.paragraph("NIF: " + this.nif);
        pdf.paragraph(this.address);
        pdf.paragraph("Email: " + this.email);
        if (!this.web.isEmpty()) {
            pdf.paragraph("Web: " + this.web);
        }

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
            String discount = "";
            if ((shopping.getDiscount().doubleValue() > 0.009) && !shopping.getArticle().getCode().equals("1")) {
                discount = "" + shopping.getDiscount().setScale(2, RoundingMode.HALF_UP);
            }

            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), "" + shopping.getAmount(), discount,
                    shopping.getShoppingTotal().setScale(2, RoundingMode.HALF_UP) + "€");

        }
        pdf.tableColspanRight("BASE IMPONIBLE: " + invoice.getBaseTax().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("IVA: " + invoice.getTax().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.tableColspanRight("TOTAL: " + invoice.getTicket().getTotal().setScale(2, RoundingMode.HALF_UP) + "€");
        pdf.line();
        pdf.paragraphEmphasized("Gracias por su visita").paragraphEmphasized(" ").line();
        return pdf.build();

    }

}
