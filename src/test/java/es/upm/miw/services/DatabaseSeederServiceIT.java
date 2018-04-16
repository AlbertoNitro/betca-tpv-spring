package es.upm.miw.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class DatabaseSeederServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @Test
    public void testUserSeedDatabase() {
        // this.databaseSeederService.deleteAllAndCreateAdmin();
        // this.databaseSeederService.seedDatabase("tpv-db-test.yml");
        User user = userRepository.findByMobile("666666001");
        assertNotNull(user);
        assertEquals("u001", user.getUsername());
        assertEquals("u001@gmail.com", user.getEmail());
        assertEquals("66666600L", user.getDni());
        assertTrue(user.getRoles().length >= 2);
    }

    @Test
    public void testExpandArticle() {
        Article article = new Article("1", "Pantalón Gris[15.99,17.99,19,99]", BigDecimal.TEN, "Pant.Gris[2:16:2,18:26:2,28:40:2]", 5, null,
                false);
        this.databaseSeederService.expandArticlewithSizes(article);

    }

    //@Test
    public void testCreateEan13() {
        //System.out.println(this.databaseSeederService.createEan13());
    }

    //@Test
    public void testSeedDatabase() throws IOException {
        this.databaseSeederService.reset();
        this.databaseSeederService.seedDatabase("ranur.yml");
    }

}
