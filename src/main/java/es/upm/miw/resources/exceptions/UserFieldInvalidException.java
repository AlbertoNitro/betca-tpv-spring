package es.upm.miw.resources.exceptions;

public class UserFieldInvalidException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Id de item no encontrado";

    public UserFieldInvalidException() {
        this("");
    }

    public UserFieldInvalidException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
