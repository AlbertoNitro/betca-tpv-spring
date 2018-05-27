package es.upm.miw.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.AdminController;
import es.upm.miw.exceptions.FileException;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(AdminResource.ADMINS)
public class AdminResource {

    public static final String ADMINS = "/admins";

    public static final String STATE = "/state";

    public static final String DB = "/db";

    public static final String ARTICLES_WITHOUT_CODE = "/articles-without-code";

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Autowired
    private AdminController adminController;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    @DeleteMapping(value = STATE)
    public void shutDown() {
        configurableApplicationContext.close();
    }

    @DeleteMapping(value = DB)
    public void deleteDb() {
        this.adminController.deleteDb();
    }

    @DeleteMapping(value = DB + ARTICLES_WITHOUT_CODE)
    public void resetDb() {
        this.adminController.resetDb();
    }

    @PostMapping(value = DB)
    public void seedDb(@RequestBody String ymlFileName) throws FileException {
        this.adminController.seedDatabase(ymlFileName);
    }

}
