package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ArticleDto;
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
    public void before() {
        this.articleDto = this.articleController.createArticle(new ArticleDto("7000000000001", "desc", "ref", BigDecimal.TEN, 5)).get();
    }

    @Test
    public void testReadArticleVarious() {
        assertEquals("1", this.articleController.readArticle("1").get().getCode());
    }

    @Test
    public void testCreateArticle() {
        assertEquals("7000000000001", this.articleDto.getCode());
        assertEquals(new Integer(5), this.articleDto.getStock());
        assertNotNull(this.articleDto.getRegistrationDate());
    }

    @Test
    public void testCreateArticleWithoutCode() {
        ArticleDto articleDto = new ArticleDto(null, "without code", "ref", BigDecimal.TEN, 5);
        articleDto = this.articleController.createArticle(articleDto).get();
        assertNotNull(articleDto.getCode());
        this.articleRepository.delete(articleDto.getCode());
    }

    @Test
    public void testCreateArticleWithoutStock() {
        ArticleDto articleDto = new ArticleDto(null, "without code", "ref", BigDecimal.TEN, null);
        articleDto = this.articleController.createArticle(articleDto).get();
        assertEquals(new Integer(0), articleDto.getStock());
        this.articleRepository.delete(articleDto.getCode());
    }

    @Test
    public void testUpdateArticle() {
        this.articleDto.setDescription("new");
        this.articleDto.setReference("new");
        this.articleDto.setRetailPrice(BigDecimal.TEN);
        this.articleDto.setStock(10);
        Optional<String> result = this.articleController.updateArticle(this.articleDto.getCode(), this.articleDto);
        assertEquals("new", this.articleRepository.findOne(this.articleDto.getCode()).getDescription());
        assertEquals("new", this.articleRepository.findOne(this.articleDto.getCode()).getReference());
        assertEquals(BigDecimal.TEN, this.articleRepository.findOne(this.articleDto.getCode()).getRetailPrice());
        assertEquals(new Integer(10), this.articleRepository.findOne(this.articleDto.getCode()).getStock());
        assertFalse(result.isPresent());
    }
    
    @Test
    public void testUpdateArticleNotFound() {
        assertTrue(this.articleController.updateArticle("666", this.articleDto).isPresent());
    }
    
    @Test
    public void testUpdateArticleStock() {
        Optional<String> result = this.articleController.updateArticleStock(this.articleDto.getCode(), 10);
        assertEquals(new Integer(10), this.articleRepository.findOne(this.articleDto.getCode()).getStock());
        assertFalse(result.isPresent());
    }
    
    @Test
    public void testUpdateArticleStockNotFound() {
        assertTrue(this.articleController.updateArticleStock("666", 10).isPresent());
    }
    
 
    @After
    public void delete() {
        this.articleRepository.delete(this.articleDto.getCode());
    }

}
