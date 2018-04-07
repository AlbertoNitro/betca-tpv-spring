package es.upm.miw.services;

import java.io.File;
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
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class PdfTicketBuilder extends PdfBuilder {

    private static final int QR_CODE_PERCENT = 50;

    private static final int BAR_CODE_HEIGHT = 50;

    private static final int BAR_CODE_WIDTH_PERCENT_MIN = 25;

    private static final int BAR_CODE_WIDTH_PERCENT_LENGHT = 5;

    private static final int TERMIC_FONT_SIZE = 7;

    private static final int TERMIC_FONT_SIZE_EMPHASIZEDD = 10;

    private static final int TERMIC_MARGIN = 4;

    private static final float TERMIC_PAGE_WIDHT = 227;

    private static final float TERMIC_PAGE_HEIGHT = 300;

    private static final float LINE_WIDTH = 0.5f;

    private static final int LINE_GAP = 2;

    private static final int IMAGE_WIDTH = 80;

    public PdfTicketBuilder(String path, int lines) {
        super(path);
        this.prepareDocument(new PageSize(TERMIC_PAGE_WIDHT, TERMIC_PAGE_HEIGHT + lines * 15));
        this.getDocument().setMargins(TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN, TERMIC_MARGIN);
        this.getDocument().setFontSize(TERMIC_FONT_SIZE);
        this.getDocument().setTextAlignment(TextAlignment.LEFT);
    }

    public PdfTicketBuilder paragraphEmphasized(String text) {
        this.getDocument().add(new Paragraph(text).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD));
        return this;
    }

    public PdfTicketBuilder paragraph(String text) {
        this.getDocument().add(new Paragraph(text));
        return this;
    }

    public PdfTicketBuilder barCode(String code) {
        Barcode128 code128 = new Barcode128(this.getDocument().getPdfDocument());
        code128.setCodeType(Barcode128.CODE128);
        code128.setCode(code.trim().replace('-', '/').replace('_', '?'));  
        code128.setAltText(code.trim());
        Image code128Image = new Image(code128.createFormXObject(this.getDocument().getPdfDocument()));
        int width = code.length() * BAR_CODE_WIDTH_PERCENT_LENGHT + BAR_CODE_WIDTH_PERCENT_MIN;
        if (width > 100) {
            width = 95;
        }
        code128Image.setWidthPercent(width);
        code128Image.setHeight(BAR_CODE_HEIGHT);
        code128Image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.getDocument().add(code128Image);
        return this;
    }

    public PdfTicketBuilder qrCode(String code) {
        BarcodeQRCode qrcode = new BarcodeQRCode(code.trim());
        Image qrcodeImage = new Image(qrcode.createFormXObject(this.getDocument().getPdfDocument()));
        qrcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrcodeImage.setWidthPercent(QR_CODE_PERCENT);
        this.getDocument().add(qrcodeImage);
        Paragraph paragraph = new Paragraph("Ref. " + code);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        this.getDocument().add(paragraph);
        return this;
    }

    public PdfTicketBuilder line() {
        DottedLine separator = new DottedLine();
        separator.setGap(LINE_GAP);
        separator.setLineWidth(LINE_WIDTH);
        this.getDocument().add(new LineSeparator(separator));
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
            this.getDocument().add(img);
        } catch (MalformedURLException mue) {
            Logger.getLogger(this.getClass()).error("File: " + mue);
        }
        return this;
    }

    public PdfTicketBuilder tableColumnsSizes(float... widths) {
        this.setTable(new Table(widths, true));
        this.getTable().setBorder(new SolidBorder(Color.WHITE, 2));
        this.getTable().setVerticalAlignment(VerticalAlignment.MIDDLE);
        this.getTable().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.getTable().setTextAlignment(TextAlignment.RIGHT);
        return this;
    }

    public PdfTicketBuilder tableColumnsHeader(String... headers) {
        for (String header : headers) {
            this.getTable().addHeaderCell(header);
        }
        return this;
    }

    public PdfTicketBuilder tableCell(String... cells) {
        for (String cell : cells) {
            this.getTable().addCell(cell);
        }
        return this;
    }

    public PdfTicketBuilder tableColspanRight(String value) {
        Cell cell = new Cell(1, this.getTable().getNumberOfColumns());
        cell.setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(TERMIC_FONT_SIZE_EMPHASIZEDD);
        cell.add(value);
        this.getTable().addCell(cell);
        this.getDocument().add(this.getTable());
        return this;
    }

    public Optional<byte[]> build() {
        this.getDocument().close();
        try {
            return Optional.of(Files.readAllBytes(new File(this.getFullPath()).toPath()));
        } catch (IOException ioe) {
            Logger.getLogger(this.getClass()).error("IO: " + ioe);
        }
        return Optional.empty();
    }

}
