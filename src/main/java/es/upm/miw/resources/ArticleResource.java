package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.output.ArticleDto;
import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    private static final String VARIOS = "1";

    public static final String ARTICLES = "/articles";

    public static final String CODE_ID = "/{code}";

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = CODE_ID, method = RequestMethod.GET)
    //TODO TERMINARLO BIEN
    public ArticleDto readArticle(@PathVariable String code) throws ArticleCodeNotFoundException {
        Article article = this.articleRepository.findOne(code);
        if (article == null && code.length() < 5) {
            article = this.articleRepository.findOne(VARIOS);
        }
        if (article == null) {
            throw new ArticleCodeNotFoundException(code);
        }
        return new ArticleDto(article);

    }

}
