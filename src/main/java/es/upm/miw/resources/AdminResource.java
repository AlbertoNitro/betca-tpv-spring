package es.upm.miw.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.AdminController;
import es.upm.miw.resources.exceptions.FileNotFoundException;

@RestController
@RequestMapping(AdminResource.ADMINS)
public class AdminResource {

    public static final String ADMINS = "/admins";

    public static final String STATE = "/state";

    public static final String DB = "/db";

    @Autowired
    private AdminController adminController;

    @RequestMapping(value = STATE, method = RequestMethod.GET)
    public String getState() {
        return "{\"state\":\"ok\"}";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = DB, method = RequestMethod.DELETE)
    public void deleteDb() {
        this.adminController.deleteDb();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = DB, method = RequestMethod.POST)
    public void seedDb(@RequestBody String ymlFileName) throws FileNotFoundException {
        Optional<String> error = this.adminController.seedDatabase(ymlFileName);
        if (error.isPresent()) {
            throw new FileNotFoundException(error.get());
        }
    }

}
