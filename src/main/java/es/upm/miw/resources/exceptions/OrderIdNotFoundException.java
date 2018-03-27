package es.upm.miw.resources.exceptions;

public class OrderIdNotFoundException extends Exception {
    private static final long serialVersionUID = -1224432235;

    public static final String DESCRIPTION = "Order id not found";

    public OrderIdNotFoundException() {
        super(DESCRIPTION);
    }

    public OrderIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
