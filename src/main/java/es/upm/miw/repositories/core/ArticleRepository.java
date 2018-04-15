package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleDto;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {

    @Query(value = "{'code' : ?0}", fields = "{'reference' : 1, 'description' : 1, 'retailPrice' : 1, 'stock' : 1}")
    ArticleDto findMinimumByCode(String code);
    
    @Query(value = "{}", fields = "{'description' : 1}")
    List<Article> findAllMinimum();

    Article findArticleByCode(String string);
  
    @Query(value = "{$or:["
            + "{reference : {$in : [null, ''] }},"
            + "{description : {$in : [null, ''] }},"
            + "{retailPrice : {$in : [null, 0] }},"
            + "{stock : null },"
            + "{provider : null }"
            + "]}", 
            fields = "{description : 1}")
    List<ArticleDto> findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();

    List<Article> findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCase(String reference, String description);

    List<Article> findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider(String reference, String description,
            String provider);
    
    Article findFirstByCodeStartingWithOrderByCodeDesc(String code);
    
    int deleteByCodeStartingWith(String code);

}
