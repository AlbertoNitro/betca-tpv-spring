package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.OrderBody;
import es.upm.miw.dtos.OrderBodyDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.OrderBodyRepository;

@Controller
public class OrderBodyController {

    @Autowired
    private OrderBodyRepository orderBodyRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public OrderBodyController() {
        // TODO Auto-generated constructor stub
    }

    public void createOrderBody(OrderBodyDto orderBodyDto) {
        OrderBody orderBody = new OrderBody(orderBodyDto.getId(), orderBodyDto.getId_order(), orderBodyDto.getId_article());
        this.orderBodyRepository.save(orderBody);
    }

    public OrderBody findById(String id) {
        return this.orderBodyRepository.findById(id);
    }

    public List<OrderBodyDto> findAllByIdOrder(String id) {
        List<OrderBody> orderBodyList = this.orderBodyRepository.findAll();
        List<OrderBodyDto> orderBodyDtoList = new ArrayList<OrderBodyDto>();
        for (OrderBody orderBody : orderBodyList) {
            if (orderBody.getId_order().equals(id)) {
                String article_name = this.articleRepository.findArticleByCode(orderBody.getId_article()).getDescription();
                orderBodyDtoList.add(new OrderBodyDto(orderBody.getId(), orderBody.getId_order(), orderBody.getId_article(), article_name));
            }
        }
        return orderBodyDtoList;
    }

}
