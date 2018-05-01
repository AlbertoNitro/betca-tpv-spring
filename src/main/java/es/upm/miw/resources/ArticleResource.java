package es.upm.miw.resources;

import java.util.List;
import java.util.Optional;

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
import es.upm.miw.resources.exceptions.ArticleException;

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
    public ArticleDto readArticle(@PathVariable String code) throws ArticleException {
        return this.articleController.readArticle(code).orElseThrow(() -> new ArticleException("Code (" + code + ") not found"));
    }

    @PostMapping
    public ArticleDto createArticle(@Valid @RequestBody ArticleDto articleDto) throws ArticleException {
        return this.articleController.createArticle(articleDto)
                .orElseThrow(() -> new ArticleException("Code (" + articleDto.getCode() + ") already exist"));
    }

    @PutMapping(value = CODE_ID)
    public void putArticle(@PathVariable String code, @Valid @RequestBody ArticleDto articleDto) throws ArticleException {
        Optional<String> error = this.articleController.updateArticle(code, articleDto);
        if (error.isPresent()) {
            throw new ArticleException(error.get());
        }
    }

    @PatchMapping(value = CODE_ID)
    public void patchArticleStock(@PathVariable String code, @RequestBody ArticleDto articleDto) throws ArticleException {
        Optional<String> error = this.articleController.updateArticleStock(code, articleDto.getStock());
        if (error.isPresent()) {
            throw new ArticleException(error.get());
        }
    }

    @GetMapping(value = INCOMPLETES)
    public List<ArticleDto> findIncompletes() {
        return this.articleController.findIncompletes();
    }

    @GetMapping(value = SEARCH)
    public List<ArticleDto> findByFieldsWithAnd(@RequestParam(required = false) String reference,
            @RequestParam(required = false) String description, @RequestParam(required = false) String provider) {
        return this.articleController.findByFieldsWithAnd(reference, description, provider);
    }

}
