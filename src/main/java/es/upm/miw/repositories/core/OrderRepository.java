package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
