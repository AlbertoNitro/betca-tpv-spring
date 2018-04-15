package es.upm.miw.resources;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.UserController;
import es.upm.miw.documents.core.Role;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/users";

    public static final String MOBILE_ID = "/{mobile}";

    public static final String SEARCH = "/search";

    @Autowired
    private UserController userController;

    @PostMapping
    public void createCustomer(@Valid @RequestBody UserDto userDto) throws UserFieldAlreadyExistException {
        if (userDto.getPassword() == null) {
            userDto.setPassword(UUID.randomUUID().toString());
        }
        if (this.userController.existsMobile(userDto.getMobile())) {
            throw new UserFieldAlreadyExistException("Existing mobile");
        }
        if (this.userController.emailRepeated(userDto)) {
            throw new UserFieldAlreadyExistException("Existing email");
        }
        if (this.userController.dniRepeated(userDto)) {
            throw new UserFieldAlreadyExistException("Existing dni");
        }
        this.userController.createUser(userDto, new Role[] {Role.CUSTOMER});
    }

    @PutMapping(value = MOBILE_ID)
    public void putCustomer(@PathVariable String mobile, @Valid @RequestBody UserDto userDto)
            throws ForbiddenException, UserIdNotFoundException, UserFieldAlreadyExistException {
        if (!this.userController.existsMobile(mobile)) {
            throw new UserIdNotFoundException("Not existing mobile");
        }
        if (this.userController.mobileRepeated(mobile, userDto)) {
            throw new UserFieldAlreadyExistException("Existing mobile");
        }
        if (this.userController.emailRepeated(mobile, userDto)) {
            throw new UserFieldAlreadyExistException("Existing email");
        }
        if (this.userController.dniRepeated(mobile, userDto)) {
            throw new UserFieldAlreadyExistException("Existing dni");
        }
        if (!this.userController.putUser(mobile, userDto, new Role[] {Role.CUSTOMER})) {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping(value = MOBILE_ID)
    public void deleteCustomer(@PathVariable String mobile) throws ForbiddenException {
        if (!this.userController.deleteUser(mobile, new Role[] {Role.CUSTOMER})) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = MOBILE_ID, method = RequestMethod.GET)
    public UserDto readCustomer(@PathVariable String mobile) throws UserIdNotFoundException {
        return this.userController.readUser(mobile, new Role[] {Role.CUSTOMER}).orElseThrow(() -> new UserIdNotFoundException(mobile));
    }

    @GetMapping
    public List<UserMinimumDto> readCustomerAll() {
        return this.userController.readCustomerAll();
    }

    @GetMapping(value = SEARCH)
    public List<UserMinimumDto> readFilterUser(@RequestParam(defaultValue = "") String mobile, @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String dni, @RequestParam(defaultValue = "") String address) {
        return this.userController.find(mobile, username, dni, address);
    }

}
