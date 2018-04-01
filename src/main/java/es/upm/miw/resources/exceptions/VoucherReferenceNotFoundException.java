package es.upm.miw.resources.exceptions;

public class VoucherReferenceNotFoundException extends Exception {

    private static final long serialVersionUID = 6546553369067053261L;

    public static final String DESCRIPTION = "Voucher reference not found";

    public VoucherReferenceNotFoundException() {
        super(DESCRIPTION);
    }

    public VoucherReferenceNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
