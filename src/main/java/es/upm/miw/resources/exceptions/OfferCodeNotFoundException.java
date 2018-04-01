package es.upm.miw.resources.exceptions;

public class OfferCodeNotFoundException extends Exception {
    private static final long serialVersionUID = -13446523452455385L;

    public static final String DESCRIPTION = "Code is not found.";

    public OfferCodeNotFoundException() {
        super(DESCRIPTION);
    }

    public OfferCodeNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
