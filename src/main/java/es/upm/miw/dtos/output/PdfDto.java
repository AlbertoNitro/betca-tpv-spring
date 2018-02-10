package es.upm.miw.dtos.output;

public class PdfDto {
    
    private byte[] pdf;

    public PdfDto() {
    }

    public PdfDto(byte[] pdf) {
        super();
        this.pdf = pdf;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

}
