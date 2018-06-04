package es.upm.miw.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.AdminController;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(AdminResource.ADMINS)
public class AdminResource {

    public static final String ADMINS = "/admins";

    public static final String STATE = "/state";
    
    public static final String DB = "/db";
    
    @Autowired
    private AdminController adminController;
    
    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    @DeleteMapping(value = STATE)
    public void shutDown() {
        configurableApplicationContext.close();
    }
    
    @PostMapping(value = DB)
    public void initializeBD() {
        this.adminController.initializeDB();
    }

}
