package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {
    @Test
    public void testGetUserRole() {
        User user = new User();
        Role[] roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER};
        user.setRoles(roles);
        assertEquals(Role.ADMIN, user.getRoles()[0]);
        assertEquals(Role.MANAGER, user.getRoles()[1]);
        assertEquals(Role.OPERATOR, user.getRoles()[2]);
        assertEquals(Role.CUSTOMER, user.getRoles()[3]);
    }

    @Test
    public void testRoleLength() {
        User user = new User();
        Role[] roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER};
        user.setRoles(roles);
        assertEquals(4, user.getRoles().length);
        roles = new Role[]{Role.ADMIN, Role.MANAGER, Role.OPERATOR};
        user.setRoles(roles);
        assertEquals(3, user.getRoles().length);
        roles = new Role[]{Role.ADMIN, Role.MANAGER};
        user.setRoles(roles);
        assertEquals(2, user.getRoles().length);
        roles = new Role[]{Role.ADMIN};
        user.setRoles(roles);
        assertEquals(1, user.getRoles().length);
        roles = new Role[]{};
        user.setRoles(roles);
        assertEquals(0, user.getRoles().length);
    }
}
