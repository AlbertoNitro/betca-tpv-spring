package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import es.upm.miw.resources.exceptions.NotFoundException;
import es.upm.miw.resources.exceptions.OrderException;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public void create(OrderDto orderDto) throws NotFoundException {
        Provider provider = this.providerRepository.findOne(orderDto.getProviderId());
        if (provider == null) {
            throw new NotFoundException("Provider (" + orderDto.getProviderId() + ")");
        }
        Order order = new Order(orderDto.getDescription(), provider,
                this.createOrderLineList(orderDto.getOrdersLine()).toArray(new OrderLine[0]));
        this.orderRepository.save(order);
    }

    private List<OrderLine> createOrderLineList(List<OrderLineDto> orderLineDtolist) throws NotFoundException {
        List<OrderLine> orderLineList = new ArrayList<>();
        for (OrderLineDto orderLineDto : orderLineDtolist) {
            Article article = this.articleRepository.findOne(orderLineDto.getArticleId());
            if (article == null) {
                throw new NotFoundException("Article (" + orderLineDto.getArticleId() + ")");
            }
            orderLineList.add(new OrderLine(article, orderLineDto.getRequiredAmount(), orderLineDto.getRequiredAmount()));
        }
        return orderLineList;
    }

    public void update(String id, OrderDto orderDto) throws NotFoundException, OrderException {
        Order order = readOne(id);
        if (order.getClosingDate() != null) {
            throw new OrderException("Already closed");
        }
        order.setDescription(orderDto.getDescription());
        order.setOrderLine(this.createOrderLineList(orderDto.getOrdersLine()).toArray(new OrderLine[0]));
        this.orderRepository.save(order);
    }

    private Order readOne(String id) throws NotFoundException {
        Order order = this.orderRepository.findOne(id);
        if (order == null) {
            throw new NotFoundException("Order(" + id + ")");
        }
        return order;
    }

    public void orderEntry(String id, OrderDto orderDto) throws NotFoundException, OrderException {
        Order order = readOne(id);
        if (order.getClosingDate() != null) {
            throw new OrderException("Already closed");
        }
        if (orderDto.getOrdersLine().size() != order.getOrderLine().length) {
            throw new OrderException("Entry List is distinct order line list of BD");
        }
        for (int i = 0; i < orderDto.getOrdersLine().size(); i++) {
            int finalAmount = orderDto.getOrdersLine().get(i).getFinalAmount();
            Article article = order.getOrderLine()[i].getArticle();
            order.getOrderLine()[i].setFinalAmount(finalAmount);
            article.setStock(article.getStock() + finalAmount);
            this.articleRepository.save(article);
        }
        order.close();
        this.orderRepository.save(order);
    }

    public OrderDto read(String id) throws NotFoundException {
        return new OrderDto(readOne(id));
    }

    public void delete(String id) throws OrderException {
        Order order = this.orderRepository.findOne(id);
        if (order.getClosingDate() != null) {
            throw new OrderException("Already closed. Cannot be deleted (" + id + ")");
        }
        this.orderRepository.delete(order);
    }

    public List<OrderBaseOutputDto> readAll() {
        List<Order> orderList = this.orderRepository.findAll(new Sort(Sort.Direction.DESC, "openingDate"));
        List<OrderBaseOutputDto> orderDtoOutputList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoOutputList.add(new OrderBaseOutputDto(order));
        }
        return orderDtoOutputList;
    }

}
