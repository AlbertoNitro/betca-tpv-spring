package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {

}
