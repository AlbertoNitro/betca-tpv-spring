package es.upm.miw.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class PdfTag24Builder {

    private static final String USER_HOME = "user.home";

    private static final String ROOT_PDFS = "/tpv/pdfs";

    private static final String PDF_FILE_EXT = ".pdf";

    private String fullPath;

    private Document document;

    private Table table;

    private int tag24 = 0;

    public PdfTag24Builder(String path) {
        fullPath = System.getProperty(USER_HOME) + ROOT_PDFS + path + PDF_FILE_EXT;
        this.prepareDocument(PageSize.A4);
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

    private PdfTag24Builder prepareTags24() {
        table = new Table(new float[] {1, 1, 1}, true);
        table.setBorder(Border.NO_BORDER);
        document.setMargins(35, 15, 0, 15);
        return this;
    }

    public PdfTag24Builder addTag24(String description, String code) {
        assert description != null;
        assert code != null;
        if ((tag24 % 24) == 0) {
            if (tag24 > 0) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
            this.prepareTags24();
        }
        Cell cell = new Cell();
        cell.setPaddingTop(10);
        cell.setMinHeight(84.2F);
        cell.setBorder(Border.NO_BORDER);
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.add(description);
        if (!code.isEmpty()) {
            BarcodeEAN barcode = new BarcodeEAN(document.getPdfDocument());
            barcode.setCodeType(BarcodeEAN.EAN13);
            barcode.setCode(code.trim());
            Image barcodeImage = new Image(barcode.createFormXObject(document.getPdfDocument()));
            barcodeImage.setWidthPercent(70);
            barcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell.add(barcodeImage);
        }
        table.addCell(cell);
        document.add(table);
        tag24++;
        return this;
    }

    public Optional<byte[]> build() {
        while (tag24 % 3 != 0) {
            Cell cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            table.addCell(cell);
            document.add(table);
            tag24++;
        }
        document.close();
        try {
            return Optional.of(Files.readAllBytes(new File(fullPath).toPath()));
        } catch (IOException ioe) {
            Logger.getLogger(this.getClass()).error("IO: " + ioe);
        }
        return Optional.empty();
    }

}
