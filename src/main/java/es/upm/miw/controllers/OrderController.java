package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.OrderLine;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.OrderBaseOutputDto;
import es.upm.miw.dtos.OrderDto;
import es.upm.miw.dtos.OrderLineDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<OrderBaseOutputDto> readAll() {
        List<Order> orderList = this.orderRepository.findAll();
        List<OrderBaseOutputDto> orderDtoOutputList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoOutputList.add(new OrderBaseOutputDto(order));
        }
        return orderDtoOutputList;
    }

    public Optional<String> create(OrderDto orderDto) {
        Provider provider = this.providerRepository.findOne(orderDto.getProviderId());
        if (provider == null) {
            return Optional.of("Provider not found. " + orderDto.getProviderId());
        }
        List<OrderLine> orderLineList = new ArrayList<>();
        for (OrderLineDto orderLineDto : orderDto.getOrdersLine()) {
            Article article = this.articleRepository.findOne(orderLineDto.getArticleId());
            if (article == null) {
                return Optional.of("Article not found. " + orderLineDto.getArticleId());
            }
            orderLineList.add(new OrderLine(article, orderLineDto.getRequiredAmount(), orderLineDto.getRequiredAmount()));
        }
        Order order = new Order(orderDto.getDescription(), provider, orderLineList.toArray(new OrderLine[0]));
        this.orderRepository.save(order);
        return Optional.empty();
    }

    public Optional<OrderDto> readOne(String id) {
        Order order = this.orderRepository.findOne(id);
        if (order == null) {
            return Optional.empty();
        }
        return Optional.of(new OrderDto(order));
    }

    public Optional<String> orderEntry(String id, OrderDto orderDto) {
        Order order = this.orderRepository.findOne(id);
        if (order == null) {
            return Optional.of("Order not found. " + id);
        }
        order.setDescription(orderDto.getDescription());
        for (int i = 0; i < orderDto.getOrdersLine().size(); i++) {
            int finalAmount = orderDto.getOrdersLine().get(i).getFinalAmount();
            Article article = order.getOrderLine()[i].getArticle();
            order.getOrderLine()[i].setFinalAmount(finalAmount);
            article.setStock(article.getStock() + finalAmount);
            this.articleRepository.save(article);
        }
        order.close();
        this.orderRepository.save(order);
        return Optional.empty();
    }

}
