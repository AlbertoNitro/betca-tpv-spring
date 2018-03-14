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
import es.upm.miw.dtos.ArticleOutputDto;
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
	public void testpostFastArticle() {
		ArticleOutputDto articulo = new ArticleOutputDto();
		articulo.setCode("1");
		articulo.setDescription("blabla");
		Number retailPrice = 2;
		articulo.setRetailPrice(new BigDecimal(retailPrice.toString()));
		restService.loginAdmin().restBuilder().path(ArticleResource.ARTICLES).body(articulo).post().build();
		assertEquals("blabla", this.articleRepository.findArticleByDescription("blabla").getDescription());
	}

    @Test
    public void testReadArticleOperator() {
        ArticleOutputDto articleOutputDto = restService.loginOperator().restBuilder(new RestBuilder<ArticleOutputDto>())
                .clazz(ArticleOutputDto.class).path(ArticleResource.ARTICLES).path(ArticleResource.CODE_ID).expand("article1").get()
                .build();
        assertEquals("article1", articleOutputDto.getCode());
    }
    
    @Test
    public void testReadAll() {
        List<ArticleOutputDto> articleOutputDto = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleOutputDto[]>())
                .clazz(ArticleOutputDto[].class).path(ArticleResource.ARTICLES).get()
                .build());
        List<ArticleOutputDto> articleOutputDto_ = articleController.readAll();
        
        assertEquals(articleOutputDto_.size(), articleOutputDto.size());
    
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
