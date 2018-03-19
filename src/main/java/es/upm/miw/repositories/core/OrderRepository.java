package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Order;
import es.upm.miw.dtos.OrderDto;

public interface OrderRepository extends MongoRepository<Order, String> {

    public Order findById(String id);

    @Query(fields = "{ '_id' : 0, 'id_provider' : 1}")
    public List<OrderDto> findFirst10();

}
