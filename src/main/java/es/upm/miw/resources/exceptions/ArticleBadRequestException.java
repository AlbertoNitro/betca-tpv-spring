package es.upm.miw.resources.exceptions;

public class ArticleBadRequestException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Article Bad Request Exception";

    public ArticleBadRequestException() {
        super(DESCRIPTION);
    }

    public ArticleBadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
