package es.upm.miw.resources;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.OrderDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class OrderResourceFunctionaTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private OrderDto orderDto;

    @Before
    public void before() {
        this.orderDto = new OrderDto("2018-3", "P1", "Provider 1");

    }

    @Test
    public void CreateOrderTest() throws Exception {
        restService.loginAdmin().restBuilder().path(OrderResource.ORDER).body(this.orderDto).post().build();
    }

}
