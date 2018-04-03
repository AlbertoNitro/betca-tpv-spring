package es.upm.miw.resources.exceptions;

public class ArticlesFamilyCreationException extends Exception {
    private static final long serialVersionUID = 7695197438670639828L;

    public static final String DESCRIPTION = "Articles Family creation exception";

    public ArticlesFamilyCreationException() {
        super(DESCRIPTION);
    }

    public ArticlesFamilyCreationException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
