package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.ArticleFamily;

public interface ArticleFamilyRepository extends MongoRepository<ArticleFamily, String> {

}
