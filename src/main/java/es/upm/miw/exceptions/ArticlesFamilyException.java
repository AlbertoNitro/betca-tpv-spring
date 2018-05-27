package es.upm.miw.exceptions;

public class ArticlesFamilyException extends Exception {
    private static final long serialVersionUID = 7695197438670639828L;

    public static final String DESCRIPTION = "Articles Family creation exception";

    public ArticlesFamilyException() {
        super(DESCRIPTION);
    }

    public ArticlesFamilyException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
