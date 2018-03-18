package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleOutputDto;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {

    @Query(value = "{'code' : ?0}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    ArticleOutputDto findMinimumByCode(String code);
    
	Article findArticleByCode(String string);

	Article findArticleByDescription(String string);

    @Query(value =  "{$or:[{'reference': {'$regex': ?0}},{'description': {'$regex': ?1}}]}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    List<ArticleOutputDto> findByCoderOrDescriptionLike(String reference,String description);
	 
}
