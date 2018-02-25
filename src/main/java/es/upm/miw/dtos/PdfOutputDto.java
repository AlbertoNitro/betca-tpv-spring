package es.upm.miw.dtos;

public class PdfOutputDto {
    
    private byte[] pdf;

    public PdfOutputDto() {
    }

    public PdfOutputDto(byte[] pdf) {
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
