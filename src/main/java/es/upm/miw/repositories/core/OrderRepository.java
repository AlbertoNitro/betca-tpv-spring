package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Order;
import es.upm.miw.dtos.OrderDto;

public interface OrderRepository extends MongoRepository<Order, String> {

    public Order findById(String id);

    List<OrderDto> findFirst10OrderById();

}
