package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.ArticlesFamily;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticlesFamiliaOutputDto;
import es.upm.miw.repositories.core.ArticlesFamilyRepository;

@Controller
public class ArticlesFamilyController {

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    public Optional<List<ArticlesFamiliaOutputDto>> readArticlesFamily(String id) {
        List<ArticlesFamiliaOutputDto> articlesFamiliaOutputDtoList = new ArrayList<>();

        ArticlesFamily articlesFamily = this.articlesFamilyRepository.findOne(id);

        if (articlesFamily == null && !"root".equals(id)) {
            return Optional.empty();
        } else { // Solo ocurre una vez en deply con BD vacias
            FamilyComposite root = new FamilyComposite(FamilyType.ARTICLES, "root", "root");
            this.articlesFamilyRepository.save(root);
            articlesFamily = root;
        }
        return Optional.of(articlesFamiliaOutputDtoList);
    }

}
