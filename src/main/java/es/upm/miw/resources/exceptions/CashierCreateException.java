package es.upm.miw.resources.exceptions;

public class CashierCreateException extends Exception {
    private static final long serialVersionUID = -1087223939569524961L;

    public static final String DESCRIPTION = "Cashier Create Exception. Cashier is opened";

    public CashierCreateException() {
        super(DESCRIPTION);
    }

    public CashierCreateException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
