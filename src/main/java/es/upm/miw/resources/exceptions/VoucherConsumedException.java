package es.upm.miw.resources.exceptions;

public class VoucherConsumedException extends Exception {

    private static final long serialVersionUID = 5817343811192068998L;

    public static final String DESCRIPTION = "The voucher is already consumed";

    public VoucherConsumedException() {
        super(DESCRIPTION);
    }

    public VoucherConsumedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
