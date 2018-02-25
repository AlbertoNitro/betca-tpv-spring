package es.upm.miw.resources.exceptions;

public class UserFieldAlreadyExistException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Campo de usuario repetido";

    public UserFieldAlreadyExistException() {
        this("");
    }

    public UserFieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
