package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleDto;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {

    @Query(value = "{code : ?0}", fields = "{reference : 1, description : 1, retailPrice : 1, stock : 1}")
    ArticleDto findMinimumByCode(String code);

    @Query(value = "{$or:[" 
       + "{reference : {$in : [null, ''] }}," 
       + "{description : {$in : [null, ''] }},"
       + "{retailPrice : {$in : [null, 0] }}," 
       + "{stock : null }," 
       + "{provider : null }"
       + "]}", fields = "{description : 1, stock : 1}")
    List<ArticleDto> findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();

    @Query(value = "{$and:[" 
       + "?#{ [0] == null ? { $where : 'true'} : { reference : {$regex:[0], $options: 'i'} } },"
       + "?#{ [1] == null ? { $where : 'true'} : { description : {$regex:[1], $options: 'i'} } },"
       + "?#{ [2] == null ? { $where : 'true'} : { provider :  [2] } }"
       + "]}", fields = "{reference : 1, description : 1, provider : 1, stock : 1}")
    List<Article> findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider(String reference, String description,
            String provider);

    Article findFirstByCodeStartingWithOrderByCodeDesc(String code);

    int deleteByCodeStartingWith(String code);

}
