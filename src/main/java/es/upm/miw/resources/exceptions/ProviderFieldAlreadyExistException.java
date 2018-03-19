package es.upm.miw.resources.exceptions;

public class ProviderFieldAlreadyExistException extends Exception {

    private static final long serialVersionUID = 1564291763389349849L;
    
    public static final String DESCRIPTION = "Provider Field Already Exist";

    public ProviderFieldAlreadyExistException() {
        super(DESCRIPTION);
    }

    public ProviderFieldAlreadyExistException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
