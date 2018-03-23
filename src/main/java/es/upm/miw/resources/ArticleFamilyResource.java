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
import es.upm.miw.dtos.ArticleOutputDto;
import es.upm.miw.dtos.FamilyOutputDto;
import es.upm.miw.resources.exceptions.ArticleFamilyNotFoudException;;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleFamilyResource.ARTICLESFAMILY)
public class ArticleFamilyResource {
    public static final String ARTICLESFAMILY = "/articlesfamily";

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

    @GetMapping(value = REFERENCE)
    public ArticleFamiliaOutputDto readAllGetArticlesOfFamily(@PathVariable String reference) throws ArticleFamilyNotFoudException {
        return this.articleFamilyController.getListArticlesOfFamily(reference)
                .orElseThrow(() -> new ArticleFamilyNotFoudException(reference));
    }

    @RequestMapping(value = FAMILY, method = RequestMethod.GET)
    public FamilyOutputDto readAllComponentFamily() {
        return this.articleFamilyController.getAllComponentFamily();
    }

    @RequestMapping(value = ARTICLES, method = RequestMethod.GET)
    public List<ArticleOutputDto> readAllArticles() {
        return this.articleController.readAll();
    }
}
