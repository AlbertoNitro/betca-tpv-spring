package es.upm.miw.businessServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class PdfTag24Builder extends PdfBuilder {

    private int tag24 = 0;

    public PdfTag24Builder(String path) {
        super(path);
        this.prepareDocument(PageSize.A4);
    }

    private void prepareTags24() {
        this.setTable(new Table(new float[] {1, 1, 1}, true));
        this.getTable().setBorder(Border.NO_BORDER);
        this.getDocument().setMargins(35, 15, 0, 15);
    }

    public PdfTag24Builder addTag24(String description, String code) {
        assert description != null;
        assert code != null;
        if ((tag24 % 24) == 0) {
            if (tag24 > 0) {
                this.getDocument().add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
            this.prepareTags24();
        }
        Cell cell = new Cell();
        cell.setPaddingTop(10);
        cell.setMinHeight(84.2F);
        cell.setBorder(Border.NO_BORDER);
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.add(description);
        if (code.length() == 13) {
            BarcodeEAN barcode = new BarcodeEAN(this.getDocument().getPdfDocument());
            barcode.setCodeType(BarcodeEAN.EAN13);
            barcode.setCode(code.trim());
            Image barcodeImage = new Image(barcode.createFormXObject(this.getDocument().getPdfDocument()));
            barcodeImage.setWidthPercent(70);
            barcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell.add(barcodeImage);
        }
        this.getTable().addCell(cell);
        this.getDocument().add(this.getTable());
        tag24++;
        return this;
    }

    public Optional<byte[]> build() {
        while (tag24 % 3 != 0) {
            Cell cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            this.getTable().addCell(cell);
            this.getDocument().add(this.getTable());
            tag24++;
        }
        this.getDocument().close();
        try {
            return Optional.of(Files.readAllBytes(new File(this.getFullPath()).toPath()));
        } catch (IOException ioe) {
            Logger.getLogger(this.getClass()).error("IO: " + ioe);
        }
        return Optional.empty();
    }

}
