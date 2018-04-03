package es.upm.miw.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.controllers.ArticlesFamilyController;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.ArticlesFamilyDto;
import es.upm.miw.resources.exceptions.ArticlesFamilyCreationException;
import es.upm.miw.resources.exceptions.ArticlesFamilyNotFoudException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticlesFamilyResource.ARTICLES_FAMILY)
public class ArticlesFamilyResource {
    public static final String ARTICLES_FAMILY = "/articles-family";

    public static final String ID_ID = "/{id}";

    public static final String CHILD_ID = "/{childId}";

    public static final String LIST = "/list";

    public static final String ARTICLE = "/article";

    @Autowired
    ArticlesFamilyController articlesFamilyController;

    @Autowired
    ArticleController articleController;

    @GetMapping(value = ID_ID + LIST)
    public List<ArticlesFamilyDto> readIdList(@PathVariable String id) throws ArticlesFamilyNotFoudException {
        return this.articlesFamilyController.readArticlesFamily(id).orElseThrow(() -> new ArticlesFamilyNotFoudException(id));
    }

    @GetMapping(value = ID_ID + ARTICLE)
    public ArticleDto readIdArticle(@PathVariable String id) throws ArticlesFamilyNotFoudException {
        return this.articlesFamilyController.readIdArticle(id).orElseThrow(() -> new ArticlesFamilyNotFoudException(id));
    }

    @PostMapping(value = ID_ID + LIST)
    public void addChild(@PathVariable String id, @RequestBody String childId) throws ArticlesFamilyNotFoudException {
        Optional<String> error = articlesFamilyController.addChild(id, childId);
        if (error.isPresent()) {
            throw new ArticlesFamilyNotFoudException(error.get());
        }
    }

    @DeleteMapping(value = ID_ID + LIST + CHILD_ID)
    public void deleteChild(@PathVariable String id, @PathVariable String childId) throws ArticlesFamilyNotFoudException {
        Optional<String> error = articlesFamilyController.deleteChild(id, childId);
        if (error.isPresent()) {
            throw new ArticlesFamilyNotFoudException(error.get());
        }
    }

    @GetMapping
    public List<ArticlesFamilyDto> readAll() {
        return this.articlesFamilyController.readAll();
    }

    @PostMapping
    public void crete(@RequestBody ArticlesFamilyDto articlesFamilyDto) throws ArticlesFamilyCreationException {
        if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLE)) {
            if (articlesFamilyDto.getArticleId() == null) {
                throw new ArticlesFamilyCreationException("Article id field must have value");
            }
        } else {
            if (articlesFamilyDto.getDescription() == null) {
                throw new ArticlesFamilyCreationException("Description field must have value");
            }
            if (articlesFamilyDto.getReference() == null) {
                throw new ArticlesFamilyCreationException("Reference field must have value");
            }
        }
        Optional<String> error = articlesFamilyController.create(articlesFamilyDto);
        if (error.isPresent()) {
            throw new ArticlesFamilyCreationException(error.get());
        }

    }

}
