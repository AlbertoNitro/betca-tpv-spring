package es.upm.miw.resources.exceptions;

public class StockAlertFieldAlreadyExistException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Stock Alert Field Already Exist";

    public StockAlertFieldAlreadyExistException() {
        super(DESCRIPTION);
    }

    public StockAlertFieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}