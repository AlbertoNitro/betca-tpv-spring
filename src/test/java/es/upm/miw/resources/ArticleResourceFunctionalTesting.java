package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.repositories.core.ArticleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleController articleController;

    @Test
    public void testReadArticle() {
        ArticleDto articleOutputDto = restService.loginAdmin().restBuilder(new RestBuilder<ArticleDto>()).clazz(ArticleDto.class)
                .path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadArticleManager() {
        ArticleDto articleOutputDto = restService.loginManager().restBuilder(new RestBuilder<ArticleDto>()).clazz(ArticleDto.class)
                .path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadArticleOperator() {
        ArticleDto articleOutputDto = restService.loginOperator().restBuilder(new RestBuilder<ArticleDto>()).clazz(ArticleDto.class)
                .path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadAllIncompletes() {
        List<ArticleDto> articleOutputDto = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleDto[]>())
                .clazz(ArticleDto[].class).path(ArticleResource.ARTICLES).path(ArticleResource.INCOMPLETES).get().build());
        List<ArticleDto> articleOutputDto_ = articleController.findIncompletes();
        assertEquals(articleOutputDto_.size(), articleOutputDto.size());

    }

    @Test
    public void testputArticle() {
        ArticleDto articulo = new ArticleDto();
        articulo.setCode("45346");
        articulo.setDescription("bleble");
        Number retailPrice = 2;
        articulo.setRetailPrice(new BigDecimal(retailPrice.toString()));

        Article articulo_ = new Article(articulo.getCode(), articulo.getDescription(), articulo.getRetailPrice());
        this.articleRepository.save(articulo_);
        articulo.setDescription("blibli");
        restService.loginAdmin().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("45346").body(articulo)
                .put().build();
        assertEquals("blibli", this.articleRepository.findOne("45346").getDescription());

    }

    @Test
    public void testReadArticleCustomer() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.loginCustomer().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get()
                .build();
    }

    @Test
    public void testReadArticleNotLogin() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.logout().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
    }
}
