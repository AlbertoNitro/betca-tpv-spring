package es.upm.miw.resources.exceptions;

public class FieldAlreadyExistException extends Exception {
    private static final long serialVersionUID = 1564291763389349849L;

    public static final String DESCRIPTION = "Field Already Exist";

    public FieldAlreadyExistException() {
        super(DESCRIPTION);
    }

    public FieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
