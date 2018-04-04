package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.FamilyArticle;

public interface FamilyArticleRepository extends MongoRepository<FamilyArticle, String> {

}
