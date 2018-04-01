package es.upm.miw.controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.dtos.ArticleFamiliaOutputDto;
import es.upm.miw.dtos.FamilyOutputDto;
import es.upm.miw.repositories.core.ArticleFamilyRepository;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.memory.ArticleComposite;
import es.upm.miw.repositories.core.memory.ArticleLeaf;
import es.upm.miw.repositories.core.memory.ComponentArticle;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyControllerIT {

    @Autowired
    ArticleFamilyController articleFamilyControlle;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFamilyRepository articleFamilyRepositories;

    @Autowired
    ArticleFamilyRepository articleFamilyRepository;

    @Test
    public void testGetAllComponent() {
        int sizeGetAllComponent = this.articleFamilyControlle.getAllComponent().count();
        assertEquals(sizeGetAllComponent, sizeGetAllComponent);
    }

    @Before
    public void before() {
        testInsertFamilysDB();
    }

    @Test
    public void testGetListaComponent() {
        this.articleFamilyControlle.getListaComponent();
        int sizeGetAllComponent = this.articleFamilyControlle.getListaComponent().size();
        assertEquals(sizeGetAllComponent, sizeGetAllComponent);
    }

    @Test
    public void testGetListaCompositeFamily() {
        ArticleFamiliaOutputDto sizeGetListaCompositeFamily = this.articleFamilyControlle.getListaCompositeFamily();
        int count = sizeGetListaCompositeFamily.getListArticles().size();
        assertEquals(ArticleFamiliaOutputDto.class, sizeGetListaCompositeFamily.getClass());
        assertEquals(sizeGetListaCompositeFamily.getListArticles().size(), count);
    }

    @Test
    public void testGetAllComponentFamily() {
        FamilyOutputDto sizeGetListaCompositeFamily = this.articleFamilyControlle.getAllComponentFamily();
        int count = sizeGetListaCompositeFamily.getListComponent().size();
        assertEquals(FamilyOutputDto.class, sizeGetListaCompositeFamily.getClass());
        assertEquals(count, sizeGetListaCompositeFamily.getListComponent().size());
    }

    @Test
    public void testGetListaArticlesOfFamily() {
        String reference = articleFamilyRepository.findAll().get(1).getReference();
        assertNotNull(this.articleFamilyControlle.getListArticlesOfFamily(reference));

    }

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

}
