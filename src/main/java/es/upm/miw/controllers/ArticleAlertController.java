package es.upm.miw.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import es.upm.miw.documents.core.ArticleAlert;
import es.upm.miw.dtos.ArticleAlertDto;
import es.upm.miw.repositories.core.ArticleAlertRepository;


public class ArticleAlertController {

    @Autowired
    private ArticleAlertRepository articleAlertRepository;

    public void createArticleAlert(ArticleAlertDto articleAlertDto) {
    		ArticleAlert articleAlert = new ArticleAlert(articleAlertDto.getArticle().getCode(), articleAlertDto.getWarningStock(), articleAlertDto.getCriticalStock(), articleAlertDto.getArticle());
        this.articleAlertRepository.save(articleAlert);
    }
   
    public boolean putArticleAlert(String code, ArticleAlertDto articleAlertDto) {
    		ArticleAlert articleAlert = this.articleAlertRepository.findByCode(code);
        assert articleAlert != null;
        articleAlert.setArticle(articleAlertDto.getArticle());
        articleAlert.setWarningStock(articleAlertDto.getWarningStock());
        articleAlert.setCriticalStock(articleAlertDto.getCriticalStock());
        this.articleAlertRepository.save(articleAlert);
        
        return true;
    }

    public boolean deleteArticleAlert(String code) {
    		ArticleAlert articleAlertBd = this.articleAlertRepository.findByCode(code);
        if (articleAlertBd == null) {
            return true;
        } else {
            this.articleAlertRepository.delete(articleAlertBd);
            return true;
        } 
    }

    public Optional<ArticleAlertDto> readArticleAlert(String code) {
    		ArticleAlert articleAlertBd = this.articleAlertRepository.findByCode(code);
        if (articleAlertBd == null) {
            return Optional.empty();
        } else {
            return Optional.of(new ArticleAlertDto(articleAlertBd));
        }
    }

    public List<ArticleAlertDto> readArticleAlertAll() {
        return this.articleAlertRepository.findArticleAlertAll();
    }

}
