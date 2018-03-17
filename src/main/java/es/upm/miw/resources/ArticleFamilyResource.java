package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.dtos.ArticleOutputDto;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleFamilyResource.ARTICLESFAMILY)
public class ArticleFamilyResource {
    public static final String ARTICLESFAMILY = "/articlesfamily";

    public static final String CODE_ID = "/{code}";

    List<ArticleOutputDto> listart = new ArrayList<ArticleOutputDto>();

    @RequestMapping(method = RequestMethod.GET)
    public List<ArticleOutputDto> readAllArticles() {
        return creaAticles();
    }

    public List<ArticleOutputDto> creaAticles() {
        BigDecimal unCentavo = new java.math.BigDecimal("0.01");
        for (int i = 0; i < 10; i++) {
            listart.add(new ArticleOutputDto("1", "1", "1", unCentavo, 1));
        }
        return listart;
    }
}
