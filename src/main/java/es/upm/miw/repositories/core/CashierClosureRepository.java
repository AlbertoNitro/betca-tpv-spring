package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.CashierClosure;

public interface CashierClosureRepository extends MongoRepository<CashierClosure, String> {
    
    CashierClosure findFirstByOrderByOpeningDateDesc();

    CashierClosure findFirstByOrderByClosureDateDesc();

}
