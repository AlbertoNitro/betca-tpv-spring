package es.upm.miw.resources.exceptions;

public class CashierClosedException extends Exception {
    private static final long serialVersionUID = 4944652811611333523L;

    public static final String DESCRIPTION = "Cashier closed exception";

    public CashierClosedException() {
        super(DESCRIPTION);
    }

    public CashierClosedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
