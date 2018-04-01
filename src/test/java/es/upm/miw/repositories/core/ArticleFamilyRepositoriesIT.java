package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.repositories.core.memory.ArticleComposite;
import es.upm.miw.repositories.core.memory.ArticleLeaf;
import es.upm.miw.repositories.core.memory.ComponentArticle;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyRepositoriesIT {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFamilyRepository articleFamilyRepositories;

    @Test
    public void testInserArticlesRepository() {
        // TODO Auto-generated method stub
        for (int i = 0; i < 10; i++) {
            articleRepository.insert(new Article("Article " + i, "Descriotion", new BigDecimal(i+"")));
        }
    }

    @Test
    public void testInsertFamilysDB() {

        Article article1 = articleRepository.findOne(articleRepository.findAll().get(0).getCode());
        Article article2 = articleRepository.findOne(articleRepository.findAll().get(1).getCode());
        Article article3 = articleRepository.findOne(articleRepository.findAll().get(2).getCode());
        Article article4 = articleRepository.findOne(articleRepository.findAll().get(3).getCode());
        Article article5 = articleRepository.findOne(articleRepository.findAll().get(4).getCode());

        ComponentArticle troncoArbol = new ArticleComposite("Familia-Ariculos");
        ComponentArticle articleLF1 = new ArticleLeaf(article1);
        ComponentArticle articleLF2 = new ArticleLeaf(article2);
        List<Article> listArtFA = new ArrayList<>();
        listArtFA.add(new ArticleLeaf(article1).getArticle());
        listArtFA.add(new ArticleLeaf(article2).getArticle());
        troncoArbol.add(articleLF1);
        troncoArbol.add(articleLF2);
        articleFamilyRepositories.insert(new ArticleFamily("Familia-Ariculos", listArtFA));

        ComponentArticle familiaMedicina = new ArticleComposite("Familia-Medicina");
        ComponentArticle articleLF3 = new ArticleLeaf(article3);
        ComponentArticle articleLF4 = new ArticleLeaf(article4);
        ComponentArticle articleLF5 = new ArticleLeaf(article5);
        List<Article> listArtFD = new ArrayList<>();
        listArtFD.add(new ArticleLeaf(article3).getArticle());
        listArtFD.add(new ArticleLeaf(article4).getArticle());
        listArtFD.add(new ArticleLeaf(article5).getArticle());
        familiaMedicina.add(articleLF3);
        familiaMedicina.add(articleLF4);
        familiaMedicina.add(articleLF5);
        articleFamilyRepositories.insert(new ArticleFamily("Familia-Medicina", listArtFD));
        assertEquals(3, familiaMedicina.count());
        assertEquals(2, troncoArbol.count());

    }

    @Test
    public void testLoadListaCompositeFamily() {
        ComponentArticle troncoArbol = new ArticleComposite("Familia-Ariculos");
        List<Article> listArt = articleRepository.findAll();

        List<ArticleFamily> listFami = articleFamilyRepositories.findAll();
        for (Article article : listArt) {
            troncoArbol.add(new ArticleLeaf(article));
        }
        for (ArticleFamily family : listFami) {
            ComponentArticle newfamilia = new ArticleComposite(family.getReference());
            for (Article article : family.getArticles()) {
                newfamilia.add(new ArticleLeaf(article));
            }
            troncoArbol.add(newfamilia);
        }
    }

    @Test
    public void testArticleFamily() {
        Article articletest = new Article("Art1", "Des1", new BigDecimal("15"));

        ComponentArticle troncoArbol = new ArticleComposite("Familia-Ariculos");

        ComponentArticle art66 = new ArticleLeaf(articletest);
        ComponentArticle art77 = new ArticleLeaf(articletest);

        ComponentArticle familiaMedicina = new ArticleComposite("Familia-Medicina");
        familiaMedicina.add(new ArticleLeaf(articletest));
        familiaMedicina.add(new ArticleLeaf(articletest));
        familiaMedicina.add(new ArticleLeaf(articletest));

        ComponentArticle familiaDesarrollo = new ArticleComposite("Familia-Desarrollo");
        familiaDesarrollo.add(new ArticleLeaf(articletest));
        familiaDesarrollo.add(new ArticleLeaf(articletest));

        troncoArbol.add(art66);
        troncoArbol.add(art77);
        troncoArbol.add(familiaDesarrollo);
        troncoArbol.add(familiaMedicina);
        assertEquals(troncoArbol.count(), troncoArbol.count());

    }

}
