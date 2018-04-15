package es.upm.miw.resources.exceptions;

public class OrderException extends Exception {
    private static final long serialVersionUID = -1344640670832805385L;

    public static final String DESCRIPTION = "Order Exception";

    public OrderException() {
        super(DESCRIPTION);
    }

    public OrderException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
