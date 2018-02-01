package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class AdminResourceFunctionalTesting {

    private static final String TPV_BD_YML = "tpv-bd.yml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${local.server.port}")
    private int port;

    @Value("${server.contextPath}")
    private String contextPath;

    @Autowired
    private RestService restService;

    @Test
    public void testState() {
        String json = new RestBuilder<String>(port).path(contextPath).path(AdminResource.ADMINS).path(AdminResource.STATE)
                .clazz(String.class).get().build();
        assertEquals("{\"state\":\"ok\"}", json);
    }

    @Test
    public void testDeleteAndSeedBd() {
        restService.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).delete().build();
        try {
            restService.loginAdmin().restBuilder().path(UserResource.USERS).path(UserResource.MOBILE_ID).expand(666666002).get().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
        }
        restService.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).body(TPV_BD_YML).post().build();
    }

    @Test
    public void testSeedBdFileNotFound() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        restService.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).body("not-file").post().build();
    }
    
    @Test
    public void testDeleteBdManagerUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginManager().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).delete().build();
    }
    
    @Test
    public void testDeleteBdOperatorUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginOperator().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).delete().build();
    }
    
    @Test
    public void testSeedBdManagerUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginManager().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).body(TPV_BD_YML).post().build();
    }

    @Test
    public void testSeedBdOperatorUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginOperator().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).body(TPV_BD_YML).post().build();
    }

}
