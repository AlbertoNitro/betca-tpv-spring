package es.upm.miw.resources.exceptions;

public class VoucherException extends Exception {

    private static final long serialVersionUID = 5817343811192068998L;

    public static final String DESCRIPTION = "Voucher Exception";

    public VoucherException() {
        super(DESCRIPTION);
    }

    public VoucherException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
