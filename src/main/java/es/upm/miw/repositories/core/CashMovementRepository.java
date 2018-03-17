package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.CashMovement;

public interface CashMovementRepository extends MongoRepository<CashMovement, String> {
	
}
