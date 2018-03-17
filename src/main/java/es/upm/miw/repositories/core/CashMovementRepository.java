package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.CashMovement;

public interface CashMovementRepository extends MongoRepository<CashMovement, String> {
	
	List<CashMovement> findByCreationDateGreaterThan(Date date);
}
