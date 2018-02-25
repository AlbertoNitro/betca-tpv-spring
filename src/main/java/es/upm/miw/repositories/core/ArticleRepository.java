package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleOutputDto;

public interface ArticleRepository extends MongoRepository<Article, String> {
    
    @Query(value = "{'code' : ?0}", fields = "{ 'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    ArticleOutputDto findMinimumByCode(String code);
    
}
