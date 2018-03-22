package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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

import es.upm.miw.controllers.OrderController;
import es.upm.miw.dtos.OrderDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class OrderResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private OrderDto orderDto;
    
    @Autowired
    private OrderController orderController;

    @Before
    public void before() {
        this.orderDto = new OrderDto("2018-3", "provider1", "Provider 1");

    }

    @Test
    public void CreateOrderTest() throws Exception {
        restService.loginAdmin().restBuilder().path(OrderResource.ORDER).body(this.orderDto).post().build();
    }
    
    @Test
    public void testReadAll() {
        List<OrderDto> orderDto = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<OrderDto[]>())
                .clazz(OrderDto[].class).path(OrderResource.ORDER).get().build());
        List<OrderDto> orderDto2 = orderController.readAll();
        
        assertEquals(orderDto2.size(),orderDto.size());
    
    }

}
