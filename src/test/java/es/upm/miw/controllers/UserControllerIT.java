package es.upm.miw.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Role;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.resources.exceptions.FieldAlreadyExistException;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerIT {

    @Autowired
    private UserController userController;

    private UserDto userDto;

    @Before
    public void before() throws FieldAlreadyExistException {
        this.userDto = new UserDto("666000000");
        this.userController.createUser(this.userDto, new Role[] {Role.CUSTOMER});
    }

    @Test(expected = FieldAlreadyExistException.class)
    public void testCreateUserMobileRepeated() throws FieldAlreadyExistException {
        this.userController.createUser(new UserDto("666666000"), new Role[] {Role.CUSTOMER});
    }

    @Test(expected = FieldAlreadyExistException.class)
    public void testCreateUserMailRepeated() throws FieldAlreadyExistException {
        this.userDto.setEmail("u004@gmail.com");
        this.userController.createUser(new UserDto("666666000"), new Role[] {Role.CUSTOMER});
    }

    @Test
    public void testUpdateUser() throws NotFoundException, FieldAlreadyExistException, ForbiddenException {
        this.userDto.setEmail("new@gmail.com");
        this.userController.updateUser(userDto.getMobile(), userDto, new Role[] {Role.CUSTOMER});
    }

    @Test(expected = FieldAlreadyExistException.class)
    public void testUpdateUserMobileRepeated() throws NotFoundException, FieldAlreadyExistException, ForbiddenException {
        this.userDto.setMobile("666666000");
        this.userController.updateUser("666000000", userDto, new Role[] {Role.CUSTOMER});
    }
    
    @Test(expected = FieldAlreadyExistException.class)
    public void testUpdateUserEmailRepeated() throws NotFoundException, FieldAlreadyExistException, ForbiddenException {
        this.userDto.setEmail("u004@gmail.com");
        this.userController.updateUser(userDto.getMobile(), userDto, new Role[] {Role.CUSTOMER});
    }
    
    @Test(expected = ForbiddenException.class)
    public void  testDeleteUserForbiddenException() throws ForbiddenException {
        this.userController.deleteUser("666666000", new Role[] {Role.CUSTOMER});
    }

    @After
    public void delete() throws ForbiddenException {
        this.userController.deleteUser("666000000", new Role[] {Role.CUSTOMER});
    }
}
