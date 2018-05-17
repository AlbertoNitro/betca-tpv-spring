package es.upm.miw.exceptions;

public class CashierException extends Exception {
    private static final long serialVersionUID = -1087223939569524961L;

    public static final String DESCRIPTION = "Cashier Exception";

    public CashierException() {
        super(DESCRIPTION);
    }

    public CashierException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
