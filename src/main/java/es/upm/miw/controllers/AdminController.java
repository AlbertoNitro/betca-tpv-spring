package es.upm.miw.controllers;

import java.io.IOException;
import java.util.Optional;

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

    public Optional<String> seedDatabase(String ymlFileName) {
        try {
            this.databaseSeederService.seedDatabase(ymlFileName);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e.getMessage());
        }
    }

}
