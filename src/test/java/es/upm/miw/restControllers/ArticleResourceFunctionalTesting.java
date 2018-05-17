package es.upm.miw.restControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.restControllers.ArticleResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testReadArticleManager() {
        assertEquals("8400000000017", this.readArticle("8400000000017").getCode());
    }

    private ArticleDto readArticle(String code) {
        return restService.loginManager().restBuilder(new RestBuilder<ArticleDto>()).clazz(ArticleDto.class).path(ArticleResource.ARTICLES)
                .path(ArticleResource.CODE_ID).expand(code).get().build();
    }

    @Test
    public void testReadArticleOperator() {
        ArticleDto articleDto = restService.loginOperator().restBuilder(new RestBuilder<ArticleDto>()).clazz(ArticleDto.class)
                .path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017").get().build();
        assertEquals("8400000000017", articleDto.getCode());
    }

    @Test
    public void testReadArticleCustomer() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.loginCustomer().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017").get()
                .build();
    }

    @Test
    public void testReadArticleNotLogin() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.logout().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017").get()
                .build();
    }

    @Test
    public void testCreateArticle() {
        ArticleDto articleDto = new ArticleDto("6000000000001", "desc", "ref", BigDecimal.TEN, 5);
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).body(articleDto).post().build();
    }

    @Test
    public void testCreateArticleAlreadyExist() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        ArticleDto articleDto = new ArticleDto("8400000000017", "desc", "ref", BigDecimal.TEN, 5);
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).body(articleDto).post().build();
    }

    @Test
    public void testPutArticle() {
        ArticleDto articleDto1 = this.readArticle("8400000000017");
        ArticleDto articleDto = new ArticleDto("8400000000017", "new", "new", BigDecimal.TEN, 5);
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017")
                .body(articleDto).put().build();
        articleDto = this.readArticle("8400000000017");
        assertEquals("new", articleDto.getDescription());
        assertEquals("new", articleDto.getReference());
        assertEquals(BigDecimal.TEN, articleDto.getRetailPrice());
        assertEquals(new Integer(5), articleDto.getStock());
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017")
                .body(articleDto1).put().build();
    }

    @Test
    public void testPutArticleNonExist() {
        ArticleDto articleDto = new ArticleDto("6000000000002", "desc", "ref", BigDecimal.TEN, 5);
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("6000000000004")
                .body(articleDto).put().build();
    }

    @Test
    public void testPathArticleStock() {
        ArticleDto articleDto1 = this.readArticle("8400000000017");
        int stock = articleDto1.getStock();
        articleDto1.setStock(6);
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017")
                .body(articleDto1).patch().build();
        articleDto1 = this.readArticle("8400000000017");
        assertEquals(new Integer(6), articleDto1.getStock());
        articleDto1.setStock(stock);
        restService.loginOperator().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("8400000000017")
                .body(articleDto1).patch().build();
    }

    @Test
    public void testReadIncompletes() {
        List<ArticleDto> articleDtoList = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleDto[]>())
                .clazz(ArticleDto[].class).path(ArticleResource.ARTICLES).path(ArticleResource.INCOMPLETES).get().build());
        assertTrue(articleDtoList.size() >= 1);
    }

    @Test
    public void testFindByFieldsWithAnd() {
        List<ArticleDto> articleDtoList = Arrays
                .asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleDto[]>()).clazz(ArticleDto[].class)
                        .path(ArticleResource.ARTICLES).path(ArticleResource.SEARCH).param("reference", "ref").get().build());
        assertTrue(articleDtoList.size() >= 6);
    }

}
