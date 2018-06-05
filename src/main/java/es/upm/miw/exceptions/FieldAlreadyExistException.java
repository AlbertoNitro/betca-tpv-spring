package es.upm.miw.exceptions;

public class FieldAlreadyExistException extends Exception {
    public static final String DESCRIPTION = "Field Already Exist";
    private static final long serialVersionUID = 1564291763389349849L;

    public FieldAlreadyExistException() {
        super(DESCRIPTION);
    }

    public FieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
