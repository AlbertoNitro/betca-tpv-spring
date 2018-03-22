package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.dtos.ArticleFamiliaOutputDto;
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

    public ArticleFamiliaOutputDto GetListaCompositeFamily() {
        ComponentArticle troncoArbol = getAllComponent();
        ArticleFamiliaOutputDto articleFamiliaOutputDto = new ArticleFamiliaOutputDto();

        for (ComponentArticle component : troncoArbol.getAllComponents()) {

            // ArticleComposite articleComposite= (ArticleComposite)Component;
            if (component.isComposite()) {
                ArticleComposite artCom = (ArticleComposite) component;
                List<Article> lstArticles = new ArrayList<>();
                for (ComponentArticle artComposite : artCom.getAllComponents()) {
                    ArticleLeaf articleLeaf = (ArticleLeaf) artComposite;
                    lstArticles.add(articleLeaf.getArticle());
                }
                articleFamiliaOutputDto.getListArticleFamily().add(new ArticleFamily(artCom.getReference(), lstArticles));
            } else {
                ArticleLeaf articleLeaf = (ArticleLeaf) component;
                articleFamiliaOutputDto.getListArticles().add(articleLeaf.getArticle());

            }

        }
        return articleFamiliaOutputDto;
    }

}
