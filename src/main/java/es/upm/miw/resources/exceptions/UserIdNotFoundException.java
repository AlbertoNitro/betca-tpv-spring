package es.upm.miw.resources.exceptions;

public class UserIdNotFoundException extends Exception {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No se encuentra el identificador de usuario utilizado";

    public UserIdNotFoundException() {
        this("");
    }

    public UserIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
