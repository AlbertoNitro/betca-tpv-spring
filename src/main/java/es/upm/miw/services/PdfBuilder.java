package es.upm.miw.services;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

public class PdfBuilder {

    public static final String USER_HOME = "user.home";

    public static final String ROOT_PDFS = "/tpv/pdfs";

    public static final String PDF_FILE_EXT = ".pdf";

    private String fullPath;

    private Document document;

    private Table table;

    public PdfBuilder(String path) {
        fullPath = System.getProperty(USER_HOME) + ROOT_PDFS + path + PDF_FILE_EXT;
   }

    public String getFullPath() {
        return fullPath;
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

    public void prepareDocument(PageSize pageSize) {
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

}
