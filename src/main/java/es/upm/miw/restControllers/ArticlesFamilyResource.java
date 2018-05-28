package es.upm.miw.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.ArticleController;
import es.upm.miw.businessControllers.ArticlesFamilyController;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.ArticlesFamilyDto;
import es.upm.miw.exceptions.ArticlesFamilyException;
import es.upm.miw.exceptions.NotFoundException;

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
    public List<ArticlesFamilyDto> readIdList(@PathVariable String id) throws NotFoundException {
        return this.articlesFamilyController.readArticlesFamily(id);
    }

    @GetMapping(value = ID_ID + ARTICLE)
    public ArticleDto readIdArticle(@PathVariable String id) throws NotFoundException {
        return this.articlesFamilyController.readIdArticle(id);
    }

    @PostMapping(value = ID_ID + LIST)
    public void addChild(@PathVariable String id, @RequestBody String childId) throws NotFoundException, ArticlesFamilyException {
        articlesFamilyController.addChild(id, childId);
    }

    @DeleteMapping(value = ID_ID + LIST + CHILD_ID)
    public void deleteChild(@PathVariable String id, @PathVariable String childId) throws NotFoundException, ArticlesFamilyException {
        this.articlesFamilyController.deleteChild(id, childId);
    }

    @GetMapping
    public List<ArticlesFamilyDto> readAll() {
        return this.articlesFamilyController.readAll();
    }

    @PostMapping
    public void create(@RequestBody ArticlesFamilyDto articlesFamilyDto) throws ArticlesFamilyException, NotFoundException {
        if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLE)) {
            if (articlesFamilyDto.getArticleId() == null) {
                throw new ArticlesFamilyException("Article id field must have value");
            }
        } else {
            if (articlesFamilyDto.getDescription() == null) {
                throw new ArticlesFamilyException("Description field must have value");
            }
            if (articlesFamilyDto.getReference() == null) {
                throw new ArticlesFamilyException("Reference field must have value");
            }
        }
        this.articlesFamilyController.create(articlesFamilyDto);
    }

    @PatchMapping(value = ID_ID)
    public void updateReferenceAndDescription(@PathVariable String id, @RequestBody ArticlesFamilyDto articlesFamilyDto)
            throws ArticlesFamilyException {
        if (articlesFamilyDto.getDescription() == null) {
            throw new ArticlesFamilyException("Description field must have value");
        }
        if (articlesFamilyDto.getReference() == null) {
            throw new ArticlesFamilyException("Reference field must have value");
        }
        this.articlesFamilyController.updateReferenceAndDescription(id, articlesFamilyDto);
    }

}
