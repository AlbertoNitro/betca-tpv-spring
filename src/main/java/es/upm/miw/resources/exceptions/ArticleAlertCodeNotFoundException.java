package es.upm.miw.resources.exceptions;

public class ArticleAlertCodeNotFoundException extends Exception {
	
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Article alert code not found";

    public ArticleAlertCodeNotFoundException() {
        super(DESCRIPTION);
    }

    public ArticleAlertCodeNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
