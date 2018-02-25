package es.upm.miw.resources.exceptions;

public class FieldInvalidException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Invalid Field";

    public FieldInvalidException() {
        this("");
    }

    public FieldInvalidException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
