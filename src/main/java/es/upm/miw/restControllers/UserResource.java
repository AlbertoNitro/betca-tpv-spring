package es.upm.miw.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.UserController;
import es.upm.miw.documents.core.Role;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.exceptions.FieldAlreadyExistException;
import es.upm.miw.exceptions.ForbiddenException;
import es.upm.miw.exceptions.NotFoundException;

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
    public void createCustomer(@Valid @RequestBody UserDto userDto) throws MethodArgumentNotValidException, FieldAlreadyExistException {
        this.userController.createUser(userDto, new Role[] {Role.CUSTOMER});
    }

    @GetMapping(value = MOBILE_ID)
    public UserDto read(@PathVariable String mobile, @RequestParam(defaultValue = "true") boolean customer,
            @AuthenticationPrincipal User activeUser) throws NotFoundException, ForbiddenException {
        if (customer) {
            return this.userController.readUser(mobile, new Role[] {Role.CUSTOMER});
        } else {
            return this.userController.readUser(mobile, activeUser.getUsername());
        }
    }

    @PutMapping(value = MOBILE_ID)
    public void putCustomer(@PathVariable String mobile, @Valid @RequestBody UserDto userDto)
            throws MethodArgumentNotValidException, ForbiddenException, NotFoundException, FieldAlreadyExistException {
        this.userController.updateUser(mobile, userDto, new Role[] {Role.CUSTOMER});
    }

    @PatchMapping(value = MOBILE_ID)
    public void updateOwnUser(@PathVariable String mobile, @Valid @RequestBody UserDto userDto, @AuthenticationPrincipal User activeUser)
            throws MethodArgumentNotValidException, NotFoundException, FieldAlreadyExistException, ForbiddenException {
        this.userController.updateOwnUser(mobile, userDto, activeUser.getUsername());
    }

    @DeleteMapping(value = MOBILE_ID)
    public void deleteCustomer(@PathVariable String mobile) throws ForbiddenException {
        this.userController.deleteUser(mobile, new Role[] {Role.CUSTOMER});
    }

    @GetMapping
    public List<UserMinimumDto> readCustomerAll() {
        return this.userController.readCustomerAll();
    }

    @GetMapping(value = SEARCH)
    public List<UserMinimumDto> readFilterUser(@RequestParam(required = false) String mobile,
            @RequestParam(required = false) String username, @RequestParam(required = false) String dni,
            @RequestParam(required = false) String address) {
        return this.userController.find(mobile, username, dni, address);
    }

}
