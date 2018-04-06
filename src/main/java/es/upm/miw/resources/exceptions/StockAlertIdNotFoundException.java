package es.upm.miw.resources.exceptions;

public class StockAlertIdNotFoundException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Stock Alert id not found";

    public StockAlertIdNotFoundException() {
        super(DESCRIPTION);
    }

    public StockAlertIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}