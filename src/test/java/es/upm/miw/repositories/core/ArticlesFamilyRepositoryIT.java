package es.upm.miw.repositories.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.FamilyArticle;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.FamilyType;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticlesFamilyRepositoryIT {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    @Test
    public void testSaveAndFindOne() {
        Article article = Article.builder().code("8400000000017").reference("reference1").description("description1").retailPrice("10").build();
        this.articleRepository.save(article);
        
        FamilyArticle familyArticle = new FamilyArticle(article);
        this.articlesFamilyRepository.save(familyArticle);
        
        FamilyComposite familyComposite = new FamilyComposite(FamilyType.ARTICLES, "FM1", "familia1");
        familyComposite.add(familyArticle);
        this.articlesFamilyRepository.save(familyComposite);

        assertNotNull(this.articlesFamilyRepository.findOne(familyComposite.getId()));
        
        this.articlesFamilyRepository.delete(familyComposite);
        this.articlesFamilyRepository.delete(familyArticle);
        this.articleRepository.delete(article);
    }
  
    

}
