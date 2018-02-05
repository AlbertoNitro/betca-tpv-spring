package es.upm.miw.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class DatabaseSeederServiceIT {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSeedDatabase() {
        // this.databaseSeederService.deleteAllAndCreateAdmin();
        // this.databaseSeederService.seedDatabase("tpv-bd-test.yml");
        User user = userRepository.findByMobile(666666001L);
        assertNotNull(user);
        assertEquals("u001", user.getUsername());
        assertEquals("u001@gmail.com", user.getEmail());
        assertEquals("66666600L", user.getDni());
        assertTrue(user.getRoles().length >= 2);
    }
    
}
