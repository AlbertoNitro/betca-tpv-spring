package es.upm.miw.resources.exceptions;

public class SchedulerIdNotFoundException extends Exception {
    private static final long serialVersionUID = 1564298763389349849L;

    public static final String DESCRIPTION = "Scheduler id not found";

    public SchedulerIdNotFoundException() {
        super(DESCRIPTION);
    }


    public SchedulerIdNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
