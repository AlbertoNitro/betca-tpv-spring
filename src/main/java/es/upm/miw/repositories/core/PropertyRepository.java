package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Property;

public interface PropertyRepository extends MongoRepository<Property, String> {

}
