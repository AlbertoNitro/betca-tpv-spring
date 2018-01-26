package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.users.Role;
import es.upm.miw.dtos.output.TokenDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TokenResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${local.server.port}")
    private int port;

    @Value("${server.contextPath}")
    private String contextPath;

    @Value("${miw.admin.mobile}")
    private String mobile;

    @Value("${miw.admin.password}")
    private String password;

    @Test
    public void testLoginAdmin() {
        TokenDto tokenDto = new RestBuilder<TokenDto>(port).path(contextPath).path(TokenResource.TOKENS)
                .basicAuth(this.mobile, this.password).clazz(TokenDto.class).post().build();
        assertEquals(Role.ADMIN, tokenDto.getRoles()[0]);
    }

    @Test
    public void testLoginAdminUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<TokenDto>(port).path(contextPath).path(TokenResource.TOKENS).basicAuth(this.mobile, "kk").clazz(TokenDto.class)
                .post().build();
    }

    @Test
    public void testLoginUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<TokenDto>(port).path(contextPath).path(TokenResource.TOKENS).basicAuth("kk", "kk").clazz(TokenDto.class).post()
                .build();
    }

    @Test
    public void testLoginNoHeaderUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<TokenDto>(port).path(contextPath).path(TokenResource.TOKENS).clazz(TokenDto.class).post().build();
    }

}
