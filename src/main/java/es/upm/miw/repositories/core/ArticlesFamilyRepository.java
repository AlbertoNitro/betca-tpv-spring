package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.ArticlesFamily;

public interface ArticlesFamilyRepository extends MongoRepository<ArticlesFamily, String> {

}
