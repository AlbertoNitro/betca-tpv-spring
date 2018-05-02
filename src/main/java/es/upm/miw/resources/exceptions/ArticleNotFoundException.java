package es.upm.miw.resources.exceptions;

public class ArticleNotFoundException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Article Not Found Exception";

    public ArticleNotFoundException() {
        super(DESCRIPTION);
    }

    public ArticleNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
