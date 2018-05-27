package es.upm.miw.exceptions;

public class TicketIdNotFoundException extends Exception {
    private static final long serialVersionUID = 2107089008371582064L;

    public static final String DESCRIPTION = "Ticket id not found";

    public TicketIdNotFoundException() {
        super(DESCRIPTION);
    }

    public TicketIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
