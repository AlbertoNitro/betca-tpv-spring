package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class OrderTest {
    private OrderResource orderResource;

    @Before
    public void before() {
        this.orderResource = new OrderResource();
    }

    @Test
    public void FirstTest() throws Exception {
        assertEquals("ORDER", this.orderResource.readOrder("1"));
    }

}
