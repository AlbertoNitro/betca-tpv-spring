package es.upm.miw.businessControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.dataServices.DatabaseSeederService;

@Controller
public class AdminController {

    @Autowired
    private DatabaseSeederService databaseSeederService;

    public void deleteDb() {
        this.databaseSeederService.deleteAllAndCreateAdmin();
    }

    public void seedDatabase() {
            this.databaseSeederService.seedDatabase();
    }

    public void resetDb() {
        this.databaseSeederService.reset();
    }

}
