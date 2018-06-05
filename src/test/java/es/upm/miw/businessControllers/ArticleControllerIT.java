package es.upm.miw.businessControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.businessControllers.ArticleController;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.exceptions.FieldAlreadyExistException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleControllerIT {

    @Autowired
    private ArticleController articleController;

    @Autowired
    private ArticleRepository articleRepository;

    private ArticleDto articleDto;

    @Before
    public void before() throws FieldAlreadyExistException {
        this.articleDto = this.articleController.createArticle(new ArticleDto("7000000000001", "desc", "ref", BigDecimal.TEN, 5));
    }

    @Test
    public void testReadArticleVarious() throws NotFoundException {
        assertEquals("1", this.articleController.readArticle("1").getCode());
    }

    @Test
    public void testCreateArticle() {
        assertEquals("7000000000001", this.articleDto.getCode());
        assertEquals(new Integer(5), this.articleDto.getStock());
        assertNotNull(this.articleDto.getRegistrationDate());
    }

    @Test
    public void testCreateArticleWithoutCode() throws FieldAlreadyExistException {
        ArticleDto articleDto = new ArticleDto(null, "without code", "ref", BigDecimal.TEN, 5);
        articleDto = this.articleController.createArticle(articleDto);
        assertNotNull(articleDto.getCode());
        this.articleRepository.delete(articleDto.getCode());
    }

    @Test
    public void testCreateArticleWithoutStock() throws FieldAlreadyExistException {
        ArticleDto articleDto = new ArticleDto(null, "without code", "ref", BigDecimal.TEN, null);
        articleDto = this.articleController.createArticle(articleDto);
        assertEquals(new Integer(0), articleDto.getStock());
        this.articleRepository.delete(articleDto.getCode());
    }

    @Test
    public void testUpdateArticle() throws NotFoundException {
        this.articleDto.setDescription("new");
        this.articleDto.setReference("new");
        this.articleDto.setRetailPrice(BigDecimal.TEN);
        this.articleDto.setStock(10);
        this.articleDto.setProvider("provider2");
        this.articleController.updateArticle(this.articleDto.getCode(), this.articleDto);
        assertEquals("new", this.articleRepository.findOne(this.articleDto.getCode()).getDescription());
        assertEquals("new", this.articleRepository.findOne(this.articleDto.getCode()).getReference());
        assertEquals(BigDecimal.TEN, this.articleRepository.findOne(this.articleDto.getCode()).getRetailPrice());
        assertEquals(new Integer(10), this.articleRepository.findOne(this.articleDto.getCode()).getStock());
        assertEquals("provider2", this.articleRepository.findOne(this.articleDto.getCode()).getProvider().getId());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateArticleNotFound() throws NotFoundException {
        this.articleController.updateArticle("666", this.articleDto);
    }

    @Test
    public void testUpdateArticleStock() throws NotFoundException {
        this.articleController.updateArticleStock(this.articleDto.getCode(), 10);
        assertEquals(new Integer(10), this.articleRepository.findOne(this.articleDto.getCode()).getStock());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateArticleStockNotFound() throws NotFoundException {
        this.articleController.updateArticleStock("666", 10);
    }

    @After
    public void delete() {
        this.articleRepository.delete(this.articleDto.getCode());
    }

}
