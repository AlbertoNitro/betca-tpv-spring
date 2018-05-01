package es.upm.miw.resources.exceptions;

public class ArticleException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Article Exception";

    public ArticleException() {
        super(DESCRIPTION);
    }

    public ArticleException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
