package es.upm.miw.resources.exceptions;

public class InvoiceIdNotFoundException extends Exception {
    private static final long serialVersionUID = 2107089008371582064L;

    public static final String DESCRIPTION = "Invoice id not found";

    public InvoiceIdNotFoundException() {
        super(DESCRIPTION);
    }

    public InvoiceIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
