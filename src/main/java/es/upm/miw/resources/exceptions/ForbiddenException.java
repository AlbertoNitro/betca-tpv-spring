package es.upm.miw.resources.exceptions;

public class ForbiddenException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No tiene rol suficiente";

    public ForbiddenException() {
        this("");
    }

    public ForbiddenException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
