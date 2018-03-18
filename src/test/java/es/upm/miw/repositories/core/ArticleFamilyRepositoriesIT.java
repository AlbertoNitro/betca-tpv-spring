package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.repositories.core.memory.ArticleComposite;
import es.upm.miw.repositories.core.memory.ArticleLeaf;
import es.upm.miw.repositories.core.memory.ComponentArticle;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyRepositoriesIT {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testFindOne() {
        assertEquals("article1", articleRepository.findOne("article1").getCode());
    }

    @Test
    public void ArticleFamily() {
        
        ComponentArticle art33 = new ArticleLeaf("art33");
        ComponentArticle art44 = new ArticleLeaf("art44");
        ComponentArticle art55 = new ArticleLeaf("art55");

        ComponentArticle troncoArbol = new ArticleComposite("Familia-Ariculos");
        
        ComponentArticle art66 = new ArticleLeaf("art66");
        ComponentArticle art77 = new ArticleLeaf("art77");

        ComponentArticle familiaMedicina = new ArticleComposite("Familia-Medicina");
        familiaMedicina.add(new ArticleLeaf("Art1"));
        familiaMedicina.add(new ArticleLeaf("Art2"));
        familiaMedicina.add(new ArticleLeaf("Art3"));

        ComponentArticle familiaDesarrollo = new ArticleComposite("Familia-Desarrollo");
        familiaDesarrollo.add(new ArticleLeaf("Art4"));
        familiaDesarrollo.add(new ArticleLeaf("Art5"));

        troncoArbol.add(art66);
        troncoArbol.add(art77);
        troncoArbol.add(familiaDesarrollo);
        troncoArbol.add(familiaMedicina);

        troncoArbol.print(1);
        art44.print(1);
        art55.print(1);
        art33.print(2);
        
        assertEquals(troncoArbol.count(), troncoArbol.count());
        
    }

}
