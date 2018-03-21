package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.OrderBody;

public interface OrderBodyRepository  extends MongoRepository<OrderBody, String> {
    
    public OrderBody findById(String id);

}
