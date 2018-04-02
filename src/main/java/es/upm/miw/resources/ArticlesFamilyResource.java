package es.upm.miw.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.controllers.ArticlesFamilyController;
import es.upm.miw.dtos.ArticlesFamiliaOutputDto;
import es.upm.miw.resources.exceptions.ArticlesFamilyNotFoudException;;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticlesFamilyResource.ARTICLES_FAMILY)
public class ArticlesFamilyResource {
    public static final String ARTICLES_FAMILY = "/articles-family";

    public static final String ID_ID = "/{id}";

    @Autowired
    ArticlesFamilyController articlesFamilyController;

    @Autowired
    ArticleController articleController;

    @GetMapping(value = ID_ID)
    public List<ArticlesFamiliaOutputDto> readArticlesFamily(@PathVariable String id) throws ArticlesFamilyNotFoudException {
        return this.articlesFamilyController.readArticlesFamily(id).orElseThrow(() -> new ArticlesFamilyNotFoudException(id));
    }

}
