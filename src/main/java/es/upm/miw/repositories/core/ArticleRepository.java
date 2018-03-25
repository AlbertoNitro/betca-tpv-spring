package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleDto;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {

    @Query(value = "{'code' : ?0}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    ArticleDto findMinimumByCode(String code);  
    
	Article findArticleByCode(String string);

	Article findArticleByDescription(String string);

    @Query(value =  "{$and:[{'reference': {'$regex': ?0}},{'description': {'$regex': ?1}}]}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    List<ArticleDto> findByReferenceAndDescriptionLike(String reference,String description);
	
    @Query(value =  "{$and:[{'reference': {'$regex': ?0}},{'description': {'$regex': ?1}},{'provider': ?2}]}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    List<ArticleDto> findByReferenceDescriptionProvider(String reference,String description,String provider);

    @Query(value =  "{$and:[{'description': {'$regex': ?0}}]}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    List<ArticleDto> findByDescriptionLike(String description);
	
    @Query(value =  "{$and:[{'description': {'$regex': ?0}},{'provider': ?1}]}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    List<ArticleDto> findByDescriptionProvider(String description,String provider);
	 
}
