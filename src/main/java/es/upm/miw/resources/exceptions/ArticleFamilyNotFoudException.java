package es.upm.miw.resources.exceptions;

public class ArticleFamilyNotFoudException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 7695197438670639828L;

    public static final String DESCRIPTION = "Family article not found or not exist";

    public ArticleFamilyNotFoudException() {
        super(DESCRIPTION);
    }

    public ArticleFamilyNotFoudException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
