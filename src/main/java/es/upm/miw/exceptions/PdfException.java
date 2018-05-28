package es.upm.miw.exceptions;

public class PdfException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "File exception";

    public PdfException() {
        super(DESCRIPTION);
    }

    public PdfException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
