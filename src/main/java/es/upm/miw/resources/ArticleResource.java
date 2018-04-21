package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";

    public static final String INCOMPLETES = "/incompletes";

    public static final String CODE_ID = "/{code}";

    public static final String SEARCH = "/search";

    @Autowired
    private ArticleController articleController;

    @GetMapping(value = CODE_ID)
    public ArticleDto readArticle(@PathVariable String code) throws ArticleCodeNotFoundException {
        return this.articleController.readArticle(code).orElseThrow(() -> new ArticleCodeNotFoundException(code));
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody ArticleDto articleOuputDto) {
        return this.articleController.createArticle(articleOuputDto);
    }

    @GetMapping
    public List<ArticleDto> readAllArticles() {
        return this.articleController.readMinimumAll();
    }

    @GetMapping(value = INCOMPLETES)
    public List<ArticleDto> readAllArticlesIncompletes() {
        return this.articleController.readMinimumAllIncompletes();
    }

    @PutMapping(value = CODE_ID)
    public void putArticle(@PathVariable String code, @Valid @RequestBody ArticleDto articleDto) {
        this.articleController.putArticle(code, articleDto);
    }

    @GetMapping(value = SEARCH)
    public List<ArticleDto> readFilterArticle(@RequestParam(defaultValue = "") String reference,
            @RequestParam(defaultValue = "") String description, @RequestParam(required = false) String provider) {
        return this.articleController.find(reference,description,provider);
    }
    
    @PatchMapping(value = CODE_ID)
    public void patchArticleStock(@PathVariable String code, @RequestBody ArticleDto articleDto) {
        this.articleController.updateArticle(code, articleDto.getStock());
    }

}
