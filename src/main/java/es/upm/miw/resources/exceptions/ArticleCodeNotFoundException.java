package es.upm.miw.resources.exceptions;

public class ArticleCodeNotFoundException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Article code not found";

    public ArticleCodeNotFoundException() {
        this("");
    }

    public ArticleCodeNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
