package es.upm.miw.resources.exceptions;

public class BudgetIdNotFoundException extends Exception {
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Budget id not found";

    public BudgetIdNotFoundException() {
        super(DESCRIPTION);
    }

    public BudgetIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
