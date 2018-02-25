package es.upm.miw.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.dtos.ArticleOutputDto;
import es.upm.miw.repositories.core.ArticleRepository;

@Controller
public class ArticleController {
    
    private static final String VARIOUS_CODE = "1";
    @Autowired
    private ArticleRepository articleRepository;

    public Optional<ArticleOutputDto> readArticle(String code){
        ArticleOutputDto articleOutputDto = this.articleRepository.findMinimumByCode(code);
        if (articleOutputDto == null && code.length() < 5) {
            articleOutputDto = this.articleRepository.findMinimumByCode(VARIOUS_CODE);
        }
        if (articleOutputDto == null) {
            return Optional.empty();
        }else {
            return Optional.of(articleOutputDto);
        }
    }

}
