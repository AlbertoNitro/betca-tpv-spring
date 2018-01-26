package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class AdminResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${local.server.port}")
    private int port;

    @Value("${server.contextPath}")
    private String contextPath;

    @Test
    public void testStateRestBuilder() {
        String json = new RestBuilder<String>(port).path(contextPath).path(AdminResource.ADMINS).path(AdminResource.STATE)
                .clazz(String.class).get().build();
        assertEquals("{\"state\":\"ok\"}", json);
    }

}
