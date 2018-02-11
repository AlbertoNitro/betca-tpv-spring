package es.upm.miw.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class PdfTicketBuilder {
    private static final String USER_HOME = "user.home";

    private static final String ROOT_PDFS = "/tpv/pdfs";

    private static final String PDF_FILE_EXT = ".pdf";

    private static final int TERMIC_FONT_SIZE = 7;

    private static final int TERMIC_FONT_SIZE_EMPHASIZEDD = 9;

    private static final int TERMIC_MARGIN = 4;

    private static final float TERMIC_PAGE_WIDHT = 227;

    private static final float TERMIC_PAGE_HEIGHT = 600;

    private static final float LINE_WIDTH = 0.5f;

    private static final int LINE_GAP = 2;

    private static final int LINE_DASH = 1;

    private static final int IMAGE_WIDTH = 80;

    private String fullPath;

    private Document document;
    
    private Table table;

    public PdfTicketBuilder(String path) {
        fullPath = System.getProperty(USER_HOME) + ROOT_PDFS + path + PDF_FILE_EXT;
        this.prepareDocument(new PageSize(TERMIC_PAGE_WIDHT, TERMIC_PAGE_HEIGHT));
        document.setMargins(TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN);
        document.setFontSize(TERMIC_FONT_SIZE);
        document.setTextAlignment(TextAlignment.LEFT);
    }

    private void prepareDocument(PageSize pageSize) {
        File file = new File(fullPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            document = new Document(new PdfDocument(new PdfWriter(fullPath)), pageSize);
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(this.getClass()).error("File: " + fnfe);
        }
    }

    public PdfTicketBuilder paragraphEmphasized(String text) {
        document.add(new Paragraph(text).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD));
        return this;
    }

    public PdfTicketBuilder paragraph(String text) {
        document.add(new Paragraph(text));
        return this;
    }

    public PdfTicketBuilder barCode(String code) {
        Barcode128 code128 = new Barcode128(document.getPdfDocument());
        code128.setCodeType(Barcode128.CODE128);
        code128.setCode(code.trim());
        Image code128Image = new Image(code128.createFormXObject(document.getPdfDocument()));
        code128Image.setWidthPercent(50);
        code128Image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(code128Image);
        return this;
    }
    
    public PdfTicketBuilder qrCode(String code) {
        BarcodeQRCode qrcode = new BarcodeQRCode(code.trim());
        Image qrcodeImage = new Image(qrcode.createFormXObject(document.getPdfDocument()));
        qrcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrcodeImage.setWidthPercent(50);
        document.add(qrcodeImage);
        Paragraph paragraph = new Paragraph("Ref. " + code);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
        return this;
    }

    public PdfTicketBuilder separator() {
        PdfCustomDashedLineSeparator separator = new PdfCustomDashedLineSeparator();
        separator.setDash(LINE_DASH);
        separator.setGap(LINE_GAP);
        separator.setLineWidth(LINE_WIDTH);
        document.add(new LineSeparator(separator));
        return this;
    }

    private String absolutePathOfResource(String resource) {
        URL resourceURL = getClass().getClassLoader().getResource(resource);
        try {
            return Paths.get(resourceURL.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException use) {
            Logger.getLogger(this.getClass()).error("URI: " + use);
        }
        return resource;
    }

    public PdfTicketBuilder addImage(String fileName) {
        try {
            Image img = new Image(ImageDataFactory.create(this.absolutePathOfResource("img/" + fileName)));
            img.setWidth(IMAGE_WIDTH);
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
        } catch (MalformedURLException mue) {
            Logger.getLogger(this.getClass()).error("File: " + mue);
        }
        return this;
    }
    
    public PdfTicketBuilder TableColumnsSizes(float... widths) {
        table = new Table(widths, true);
        table.setBorder(new SolidBorder(Color.WHITE, 2));
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setTextAlignment(TextAlignment.RIGHT);
        return this;
    }

    public PdfTicketBuilder tableColumnsHeader(String... headers) {
        for (String header : headers) {
            table.addHeaderCell(header);
        }
        return this;
    }

    public PdfTicketBuilder tableCell(String... cells) {
        for (String cell : cells) {
            table.addCell(cell);
        }
        return this;
    }

    public PdfTicketBuilder tableColspanRight(String value) {
        Cell cell = new Cell(1, table.getNumberOfColumns());
        cell.setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD);
        cell.add(value);
        table.addCell(cell);
        document.add(table);
        return this;
    }


    public Optional<byte[]> build() {
        document.close();
        try {
            return Optional.of(Files.readAllBytes(new File(fullPath).toPath()));
        } catch (IOException ioe) {
            Logger.getLogger(this.getClass()).error("IO: " + ioe);
        }
        return Optional.empty();
    }

}
