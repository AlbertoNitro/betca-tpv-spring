package es.upm.miw.resources;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.dtos.output.ArticleDto;
import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";

    public static final String CODE_ID = "/{code}";

    @RequestMapping(value = CODE_ID, method = RequestMethod.GET)
    public ArticleDto readArticle(@PathVariable String code) throws ArticleCodeNotFoundException {
        if (code.equals("0")) {
            throw new ArticleCodeNotFoundException(code);
        } else {
            return new ArticleDto("varios", "", "Varios", new BigDecimal(code).divide(new BigDecimal("100")), 10);
        }
    }

}
