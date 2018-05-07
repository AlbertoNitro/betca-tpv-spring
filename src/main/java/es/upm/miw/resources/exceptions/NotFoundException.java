package es.upm.miw.resources.exceptions;

public class NotFoundException extends Exception {
    private static final long serialVersionUID = 6830756676887746370L;

    public static final String DESCRIPTION = "Not Found Exception";

    public NotFoundException() {
        super(DESCRIPTION);
    }

    public NotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
