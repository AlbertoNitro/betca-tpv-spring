package es.upm.miw.resources.exceptions;

public class ProviderIdNotFoundException extends Exception {

    private static final long serialVersionUID = -8280478304013851339L;
    
    public static final String DESCRIPTION = "Provider id not found";

    public ProviderIdNotFoundException() {
        super(DESCRIPTION);
    }

    public ProviderIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
