package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.CashierClosure;

public interface CashierClosureRepository extends MongoRepository<CashierClosure, String> {

    CashierClosure findFirstByOrderByOpeningDateDesc();

    CashierClosure findFirstByOrderByClosureDateDesc();
    
    @Query(value = "{'$or' : [{'closureDate': { $gt : ?0}}, {'closureDate' : ?1}]}", fields = "{'salesCash' :1,'salesCard' :1, 'closureDate' :1}")
    List<CashierClosure> findByDateBetween(Date startDate, Date endDate);

}
