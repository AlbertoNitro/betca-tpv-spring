package es.upm.miw.resources.exceptions;

public class CashierMovementException extends Exception {
    private static final long serialVersionUID = -1087223939569524961L;

    public static final String DESCRIPTION = "Cashier Movement Exception. it should be open";

    public CashierMovementException() {
        super(DESCRIPTION);
    }

    public CashierMovementException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
