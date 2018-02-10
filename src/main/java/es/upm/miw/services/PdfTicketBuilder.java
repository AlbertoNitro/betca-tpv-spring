package es.upm.miw.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class PdfTicketBuilder {
    private static final String USER_HOME = "user.home";

    private static final String ROOT_PDFS = "/tpv/pdfs";

    private static final String PDF_FILE_EXT = ".pdf";

    private static final int TERMIC_FONT_SIZE = 7;

    private static final int TERMIC_FONT_SIZE_EMPHASIZEDD = 9;

    private static final int TERMIC_MARGIN = 4;

    private static final float TERMIC_PAGE_WIDHT = 227;

    private static final float TERMIC_PAGE_HEIGHT = 600;

    private String fullPath;

    private Document document;

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
