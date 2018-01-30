package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Provider;

public interface ProviderRepository extends MongoRepository<Provider, String> {

}
