package es.upm.miw.resources.exceptions;

public class ArticlesFamilyNotFoudException extends Exception {
    private static final long serialVersionUID = 7695197438670639828L;

    public static final String DESCRIPTION = "Articles Family not found";

    public ArticlesFamilyNotFoudException() {
        super(DESCRIPTION);
    }

    public ArticlesFamilyNotFoudException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
