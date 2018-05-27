package es.upm.miw.businessControllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.dataServices.DatabaseSeederService;
import es.upm.miw.exceptions.FileException;

@Controller
public class AdminController {

    @Autowired
    private DatabaseSeederService databaseSeederService;

    public void deleteDb() {
        this.databaseSeederService.deleteAllAndCreateAdmin();
    }

    public void seedDatabase(String ymlFileName) throws FileException {
        try {
            this.databaseSeederService.seedDatabase(ymlFileName);
        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage());
        }
    }

    public void resetDb() {
        this.databaseSeederService.reset();
    }

}
