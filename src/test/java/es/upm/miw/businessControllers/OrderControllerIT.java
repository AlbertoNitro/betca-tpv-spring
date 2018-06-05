package es.upm.miw.businessControllers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessControllers.OrderController;
import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.OrderLine;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class OrderControllerIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OrderController orderController;

    private Order order;

    @Test
    public void readOrderTest() {
        OrderLine[] lines = new OrderLine[]{new OrderLine(this.articleRepository.findOne("article1"), 2, 2)};
        this.order = new Order("prueba", this.providerRepository.findOne("provider1"), lines);
        this.orderRepository.save(this.order);
        assertTrue(this.orderController.readAll().size() > 0);
        this.orderRepository.delete(this.order);
    }

}
