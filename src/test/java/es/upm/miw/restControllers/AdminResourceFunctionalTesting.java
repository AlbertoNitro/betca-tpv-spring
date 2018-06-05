package es.upm.miw.restControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class AdminResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Test
    public void testInitializeDB() {
        restService.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).post().build();
    }
}
