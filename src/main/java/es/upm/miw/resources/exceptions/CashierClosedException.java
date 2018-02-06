package es.upm.miw.resources.exceptions;

public class CashierClosedException extends Exception {
    private static final long serialVersionUID = 4944652811611333523L;

    public static final String DESCRIPTION = "No se puede cerrar una caja ya cerrada";

    public CashierClosedException() {
        this("");
    }

    public CashierClosedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
