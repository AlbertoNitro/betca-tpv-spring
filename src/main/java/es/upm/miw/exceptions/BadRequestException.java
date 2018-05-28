package es.upm.miw.exceptions;

public class BadRequestException extends Exception {
    private static final long serialVersionUID = 6830756676887746370L;

    public static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException() {
        super(DESCRIPTION);
    }

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
