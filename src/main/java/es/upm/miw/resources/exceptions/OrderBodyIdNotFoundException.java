package es.upm.miw.resources.exceptions;

public class OrderBodyIdNotFoundException extends Exception {
    private static final long serialVersionUID = -1344640670832805385L;

    public static final String DESCRIPTION = "Order Body Id not found";

    public OrderBodyIdNotFoundException() {
        super(DESCRIPTION);
    }

    public OrderBodyIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
