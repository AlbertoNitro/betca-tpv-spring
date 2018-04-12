package es.upm.miw.resources;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.AdminController;
import es.upm.miw.controllers.UserController;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(AdminResource.ADMINS)
public class AdminResource {

    public static final String ADMINS = "/admins";

    public static final String STATE = "/state";

    public static final String DB = "/db";

    public static final String USERS = "/users";

    public static final String MOBILE_ID = "/{mobile}";

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Autowired
    private AdminController adminController;

    @Autowired
    private UserController userController;

    @PreAuthorize("permitAll")
    @GetMapping(value = STATE)
    public String getState() {
        return "{\"state\":\"ok\"}";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    @DeleteMapping(value = STATE)
    public void shutDown() {
        configurableApplicationContext.close();
    }

    @DeleteMapping(value = DB)
    public void deleteDb() {
        this.adminController.deleteDb();
    }

    @PostMapping(value = DB)
    public void seedDb(@RequestBody String ymlFileName) throws FileException {
        Optional<String> error = this.adminController.seedDatabase(ymlFileName);
        if (error.isPresent()) {
            throw new FileException(error.get());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    @GetMapping(value = USERS + MOBILE_ID)
    public UserDto readUser(@PathVariable String mobile, @AuthenticationPrincipal User activeUser) throws UserIdNotFoundException {
        return this.userController.readUser(mobile, activeUser.getUsername()).orElseThrow(() -> new UserIdNotFoundException(mobile));
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
    @PutMapping(value = USERS + MOBILE_ID)
    public void updateUser(@PathVariable String mobile,  @Valid @RequestBody UserDto userDto, @AuthenticationPrincipal User activeUser) throws UserIdNotFoundException {
        this.userController.putUser(mobile, userDto, activeUser.getUsername());
    }

}
