package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TokenResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testLoginAdmin() {
        restService.loginAdmin();
        assertEquals(Role.ADMIN, restService.getTokenDto().getRoles()[0]);
    }

    @Test
    public void testLoginAdminUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder().path(TokenResource.TOKENS).basicAuth(this.restService.getAdminMobile(), "kk").post().build();
    }

    @Test
    public void testLoginUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.restBuilder().path(TokenResource.TOKENS).basicAuth("kk", "kk").post().build();
    }

    @Test 
    public void testLoginNoHeaderUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.logout().restBuilder().path(TokenResource.TOKENS).post().build();
    }

}
