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

import es.upm.miw.dtos.OrderBodyDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class OrderBodyResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private OrderBodyDto orderBodyDto;

    @Before
    public void Before() {
        this.orderBodyDto = new OrderBodyDto("OB33", "2018-1", "article1", "descrip-a1");
        this.restService.loginAdmin().restBuilder().path(OrderBodyResource.ORDERBODY).body(this.orderBodyDto).post().build();
    }

    @Test
    public void readAllOrderBodyByIdOrder() {
        String json = restService.restBuilder(new RestBuilder<String>()).clazz(String.class).path(OrderBodyResource.ORDERBODY)
                .path(OrderBodyResource.ORDERBODY_ID).expand("2018-1").get().build();
        System.out.println("----RETORNO-------->" + json);

    }

    public OrderBodyResourceFunctionalTesting() {
        // TODO Auto-generated constructor stub
    }

}
