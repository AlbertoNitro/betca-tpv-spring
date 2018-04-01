package es.upm.miw.resources.exceptions;

public class OrderAlreadyExistException extends Exception {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Order Field Already Exist";

    public OrderAlreadyExistException() {
        super(DESCRIPTION);
    }

    public OrderAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
