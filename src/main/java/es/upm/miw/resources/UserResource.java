package es.upm.miw.resources;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private UserController userController;

    @RequestMapping(method = RequestMethod.POST)
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

    @RequestMapping(method = RequestMethod.PUT)
    public void putCustomer(@Valid @RequestBody UserDto userDto)
            throws ForbiddenException, UserIdNotFoundException, UserFieldAlreadyExistException {
        if (!this.userController.existsMobile(userDto.getMobile())) {
            throw new UserIdNotFoundException("Not existing mobile");
        }
        if (this.userController.emailRepeated(userDto)) {
            throw new UserFieldAlreadyExistException("Existing email");
        }
        if (this.userController.dniRepeated(userDto)) {
            throw new UserFieldAlreadyExistException("Existing dni");
        }
        if (!this.userController.putUser(userDto, new Role[] {Role.CUSTOMER})) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = MOBILE_ID, method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable String mobile) throws ForbiddenException {
        if (!this.userController.deleteUser(mobile, new Role[] {Role.CUSTOMER})) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = MOBILE_ID, method = RequestMethod.GET)
    public UserDto readCustomer(@PathVariable String mobile) throws UserIdNotFoundException {
        return this.userController.readUser(mobile, new Role[] {Role.CUSTOMER}).orElseThrow(() -> new UserIdNotFoundException(mobile));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserMinimumDto> readCustomerAll() {
        return this.userController.readCustomerAll();
    }

}
