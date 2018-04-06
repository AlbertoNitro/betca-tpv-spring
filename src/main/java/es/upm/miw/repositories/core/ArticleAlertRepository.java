package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.ArticleAlert;
import es.upm.miw.dtos.ArticleAlertDto;

public interface ArticleAlertRepository extends MongoRepository<ArticleAlert, String> {

    public ArticleAlert findByCode(String code);
    
    @Query(value = "{}", fields = "{ 'code' : 1, warning: 1, critical: 2}")
    public List<ArticleAlertDto> findArticleAlertAll();
    
}
