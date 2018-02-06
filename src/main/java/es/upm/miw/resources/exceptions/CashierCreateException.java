package es.upm.miw.resources.exceptions;

public class CashierCreateException extends Exception {
	private static final long serialVersionUID = -1087223939569524961L;

	public static final String DESCRIPTION = "No se puede crear un nuevo cierre de caja si la último no esta cerrado";

    public CashierCreateException() {
        this("");
    }

    public CashierCreateException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
