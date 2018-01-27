package es.upm.miw.resources.exceptions;

public class UserIdNotFoundException extends Exception {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No se encuentra el identificador de usuario utilizado";

    public static final int CODE = 333;

    public UserIdNotFoundException() {
        this("");
    }

    public UserIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail + ". CODE: " + CODE);
    }

}
