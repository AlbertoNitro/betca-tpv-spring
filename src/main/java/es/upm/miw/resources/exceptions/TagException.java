package es.upm.miw.resources.exceptions;

public class TagException extends Exception {
    private static final long serialVersionUID = -1344640670832805385L;

    public static final String DESCRIPTION = "Tag Exception";

    public TagException() {
        super(DESCRIPTION);
    }

    public TagException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
