package es.upm.miw.businessServices;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.LogManager;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import es.upm.miw.exceptions.PdfException;

public class PdfBuilder {

    public static final String USER_HOME = "user.home";

    public static final String ROOT_PDFS = "/tpv/pdfs";

    public static final String PDF_FILE_EXT = ".pdf";

    private String filename;

    private Document document;

    private Table table;

    public PdfBuilder(String path) {
        this.filename = System.getProperty(USER_HOME) + ROOT_PDFS + path + PDF_FILE_EXT;
    }

    public String getFullPath() {
        return filename;
    }

    public Document getDocument() {
        return document;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void prepareDocument(PageSize pageSize) throws PdfException {
        File file = new File(this.filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(filename));
            document = new Document(pdf, pageSize);
        } catch (FileNotFoundException fnfe) {
            LogManager.getLogger(this.getClass())
                    .error("PdfBuilder::prepareDocuemnt. Error when creating the pdf document (" + this.filename + "). " + fnfe);
            throw new PdfException("Can’t create PDF (" + this.filename + ")");
        }
    }

}
