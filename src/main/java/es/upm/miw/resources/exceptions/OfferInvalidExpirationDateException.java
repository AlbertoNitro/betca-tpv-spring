package es.upm.miw.resources.exceptions;

public class OfferInvalidExpirationDateException extends Exception {
	private static final long serialVersionUID = -1344652345221355385L;

    public static final String DESCRIPTION = "The expiration date selected is already expiration.";

    public OfferInvalidExpirationDateException() {
        super(DESCRIPTION);
    }

    public OfferInvalidExpirationDateException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
