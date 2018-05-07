package es.upm.miw.resources.exceptions;

public class InvoiceException extends Exception {
    private static final long serialVersionUID = 2107089008371582064L;

    public static final String DESCRIPTION = "Invoice Exception";

    public InvoiceException() {
        super(DESCRIPTION);
    }

    public InvoiceException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
