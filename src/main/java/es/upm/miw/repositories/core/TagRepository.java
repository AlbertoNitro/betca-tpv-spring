package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {

}
