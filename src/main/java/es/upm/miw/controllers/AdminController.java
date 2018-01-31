package es.upm.miw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.services.DatabaseSeederService;

@Controller
public class AdminController {

    @Autowired
    private DatabaseSeederService databaseSeederService;
    
    public void deleteDb() {
        this.databaseSeederService.deleteAllAndCreateAdmin();
    }

}
