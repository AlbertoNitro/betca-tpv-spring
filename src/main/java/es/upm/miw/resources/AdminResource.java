package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.AdminController;

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

    @RequestMapping(value = DB, method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    public void deleteDb() {
        this.adminController.deleteDb();
    }

}
