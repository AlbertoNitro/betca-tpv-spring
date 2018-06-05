package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.Token;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.repositories.core.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void seedDb() {
        this.user = new User("666001000", "666001000", "666001000");
        this.userRepository.save(user);
    }

    @Test
    public void testFindByMobile() {
        User userBd = userRepository.findByMobile("666001000");
        assertNotNull(userBd);
        assertEquals("666001000", userBd.getUsername());
    }

    @Test
    public void testUserRolesDb() {
        User userBd = userRepository.findByMobile("666001000");
        assertNotNull(userBd);
        Role[] roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER};
        userBd.setRoles(roles);
        assertEquals(Role.ADMIN, userBd.getRoles()[0]);
        assertEquals(Role.MANAGER, userBd.getRoles()[1]);
        assertEquals(Role.OPERATOR, userBd.getRoles()[2]);
        assertEquals(Role.CUSTOMER, userBd.getRoles()[3]);
    }

    @Test
    public void testUserRolesLengthDb() {
        User userBd = userRepository.findByMobile("666001000");
        Role[] roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER};
        userBd.setRoles(roles);
        assertEquals(4, userBd.getRoles().length);
        roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR};
        userBd.setRoles(roles);
        assertEquals(3, userBd.getRoles().length);
        roles = new Role[]{Role.ADMIN, Role.MANAGER};
        userBd.setRoles(roles);
        assertEquals(2, userBd.getRoles().length);
        roles = new Role[]{Role.ADMIN};
        userBd.setRoles(roles);
        assertEquals(1, userBd.getRoles().length);
        roles = new Role[]{};
        userBd.setRoles(roles);
        assertEquals(0, userBd.getRoles().length);
    }

    @Test
    public void testFindByTokenValue() {
        Token token = new Token();
        this.user.setToken(token);
        userRepository.save(this.user);
        assertEquals(user, userRepository.findByTokenValue(token.getValue()));
    }

    @Test
    public void testFindCustomerAll() {
        List<UserMinimumDto> userList = userRepository.findCustomerAll();
        for (UserMinimumDto userMinimumDto : userList) {
            assertFalse(userMinimumDto.getMobile().equals("666666000"));
        }
    }

    @After
    public void delete() {
        this.userRepository.delete(user);
    }

}
