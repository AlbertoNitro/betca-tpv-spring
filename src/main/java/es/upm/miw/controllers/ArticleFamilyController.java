package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.dtos.ArticleFamiliaOutputDto;
import es.upm.miw.dtos.FamilyOutputDto;
import es.upm.miw.repositories.core.ArticleFamilyRepository;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.memory.ArticleComposite;
import es.upm.miw.repositories.core.memory.ArticleLeaf;
import es.upm.miw.repositories.core.memory.ComponentArticle;

@Controller
public class ArticleFamilyController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFamilyRepository articleFamilyRepositories;

    public ComponentArticle getAllComponent() {
        ComponentArticle troncoArbol = new ArticleComposite("Familia-Ariculos");
        List<Article> listArticleRepository = articleRepository.findAll();

        List<ArticleFamily> listArticleFamilyRepositories = articleFamilyRepositories.findAll();
        for (Article article : listArticleRepository) {
            troncoArbol.add(new ArticleLeaf(article));
        }
        ComponentArticle newfamilia;
        for (ArticleFamily family : listArticleFamilyRepositories) {
            newfamilia = new ArticleComposite(family.getReference());
            for (Article article : family.getArticles()) {
                newfamilia.add(new ArticleLeaf(article));
            }
            troncoArbol.add(newfamilia);
        }
        return troncoArbol;
    }

    public List<ComponentArticle> getListaComponent() {
        return ((ArticleComposite) getAllComponent()).getListComponentArticle();
    }

    public ArticleFamiliaOutputDto getListaCompositeFamily() {
        ComponentArticle troncoArbol = getAllComponent();
        ArticleFamiliaOutputDto articleFamiliaOutputDto = new ArticleFamiliaOutputDto();
        List<ArticleFamily> lstArticleFamily = new ArrayList<ArticleFamily>();
        List<Article> lstArticlesleaf = new ArrayList<Article>();
        for (ComponentArticle component : troncoArbol.getAllComponents()) {
            if (component.isComposite()) {
                ArticleComposite artCom = (ArticleComposite) component;
                List<Article> lstArticles = new ArrayList<Article>();
                for (ComponentArticle artComposite : artCom.getAllComponents()) {
                    ArticleLeaf articleLeaf = (ArticleLeaf) artComposite;
                    lstArticles.add(articleLeaf.getArticle());
                }
                lstArticleFamily.add(new ArticleFamily(artCom.getReference(), lstArticles));
            } else {
                ArticleLeaf articleLeaf = (ArticleLeaf) component;
                lstArticlesleaf.add(articleLeaf.getArticle());
            }
        }
        articleFamiliaOutputDto.setListArticleFamily(lstArticleFamily);
        articleFamiliaOutputDto.setListArticles(lstArticlesleaf);

        return articleFamiliaOutputDto;
    }

    public FamilyOutputDto getAllComponentFamily() {
        ComponentArticle troncoArbol = getAllComponent();
        FamilyOutputDto familyOutputDto = new FamilyOutputDto();
        List<Object> listComponent = new ArrayList<Object>();
        for (ComponentArticle component : troncoArbol.getAllComponents()) {
            if (component.isComposite()) {
                ArticleComposite artCom = (ArticleComposite) component;
                List<Article> lstArticles = new ArrayList<Article>();
                for (ComponentArticle artComposite : artCom.getAllComponents()) {
                    ArticleLeaf articleLeaf = (ArticleLeaf) artComposite;
                    lstArticles.add(articleLeaf.getArticle());
                }
                listComponent.add(new ArticleFamily(artCom.getReference(), lstArticles));
            } else {
                ArticleLeaf articleLeaf = (ArticleLeaf) component;
                listComponent.add(articleLeaf.getArticle());
            }
        }
        familyOutputDto.setListComponent(listComponent);
        return familyOutputDto;
    }
}
