package es.upm.miw.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.controllers.ArticleFamilyController;
import es.upm.miw.dtos.ArticleFamiliaOutputDto;
import es.upm.miw.dtos.ArticlesFamiliaOutputDto;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.FamilyOutputDto;
import es.upm.miw.resources.exceptions.ArticlesFamilyNotFoudException;;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticlesFamilyResource.ARTICLES_FAMILY)
public class ArticlesFamilyResource {
    public static final String ARTICLES_FAMILY = "/articles-family";
    
    public static final String ID_ID = "/{id}";

    public static final String FAMILY = "/family";

    public static final String ARTICLES = "/articles";

    public static final String REFERENCE = "/{reference}";

    @Autowired
    ArticleFamilyController articleFamilyController;

    @Autowired
    ArticleController articleController;

    @RequestMapping(method = RequestMethod.GET)
    public ArticleFamiliaOutputDto readAllArticleFamily() {
        return this.articleFamilyController.getListaCompositeFamily();
    }

    @GetMapping(value = ID_ID)
    public List<ArticlesFamiliaOutputDto> readArticlesFamily(@PathVariable String id) throws ArticlesFamilyNotFoudException {
        return this.articleFamilyController.readArticlesFamily(id)
                .orElseThrow(() -> new ArticlesFamilyNotFoudException(id));
    }

    @RequestMapping(value = FAMILY, method = RequestMethod.GET)
    public FamilyOutputDto readAllComponentFamily() {
        return this.articleFamilyController.getAllComponentFamily();
    }

    @RequestMapping(value = ARTICLES, method = RequestMethod.GET)
    public List<ArticleDto> readAllArticles() {
        return this.articleController.readMinimumAll();
    }
}
