package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleControllerIT {

    @Autowired
    private ArticleController articleController;

    @Test
    public void testReadArticleVarious() {
        assertEquals("1", this.articleController.readArticle("1234").get().getCode());
        assertFalse(this.articleController.readArticle("12345").isPresent());
    }

}
