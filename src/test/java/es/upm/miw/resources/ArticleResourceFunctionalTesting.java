package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

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

import es.upm.miw.dtos.ArticleOutputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testReadArticle() {
        ArticleOutputDto articleOutputDto = restService.loginAdmin().restBuilder(new RestBuilder<ArticleOutputDto>())
                .clazz(ArticleOutputDto.class).path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get()
                .build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadArticleManager() {
        ArticleOutputDto articleOutputDto = restService.loginManager().restBuilder(new RestBuilder<ArticleOutputDto>())
                .clazz(ArticleOutputDto.class).path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get()
                .build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadArticleOperator() {
        ArticleOutputDto articleOutputDto = restService.loginOperator().restBuilder(new RestBuilder<ArticleOutputDto>())
                .clazz(ArticleOutputDto.class).path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get()
                .build();
        assertEquals("article1", articleOutputDto.getCode());
    }

    @Test
    public void testReadArticleCustomer() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.loginCustomer().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
    }
    
    @Test
    public void testReadArticleNotLogin() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        restService.logout().restBuilder().path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get().build();
    }

}
