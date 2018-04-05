package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.FamilyComposite;

public interface FamilyCompositeRepository extends MongoRepository<FamilyComposite, String> {

}
