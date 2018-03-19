package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Budget;

public interface BudgetRepository extends MongoRepository<Budget, String> {

}
