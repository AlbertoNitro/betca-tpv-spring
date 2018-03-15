package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.dtos.CashierClosureOutputDto;

public interface CashierClosureRepository extends MongoRepository<CashierClosure, String> {

    CashierClosure findFirstByOrderByOpeningDateDesc();

    CashierClosure findFirstByOrderByClosureDateDesc();
    
    @Query(value = "{closureDate: { $gte : ?0, $lte : ?0}}", fields = "{'salesCash' :1,'salesCard' :1, 'closureDate' :1}")
	List<CashierClosureOutputDto> findAllStatics(String dateStart, String dateFinish);

}
