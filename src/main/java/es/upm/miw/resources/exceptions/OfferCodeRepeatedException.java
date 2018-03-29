package es.upm.miw.resources.exceptions;

public class OfferCodeRepeatedException extends Exception {
    private static final long serialVersionUID = -13446523452455385L;

    public static final String DESCRIPTION = "Offer codde is repeated";

    public OfferCodeRepeatedException() {
        super(DESCRIPTION);
    }

    public OfferCodeRepeatedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
