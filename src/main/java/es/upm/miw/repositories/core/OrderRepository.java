package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Order;
import es.upm.miw.dtos.OrderDto;

public interface OrderRepository extends MongoRepository<Order, String> {

    public Order findById(String id);

    @Query(value = "{'roles' : 'CUSTOMER'}", fields = "{ '_id' : 0, 'mobile' : 1, 'username' : 1}")
    public List<OrderDto> findOrderAll();

}
