package es.upm.miw.resources.exceptions;

public class FileNotFoundException extends Exception {

    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "No se encuentra el fichero yml";

    public FileNotFoundException() {
        this("");
    }

    public FileNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
