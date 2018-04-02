package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticleFamiliaOutputDto;
import es.upm.miw.dtos.ArticlesFamiliaOutputDto;
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

    public Optional<ArticleFamiliaOutputDto> getListArticlesOfFamily(String reference) {
        ComponentArticle troncoArbol = getAllComponent();
        ArticleFamiliaOutputDto articleFamiliaOutputDto = new ArticleFamiliaOutputDto();
        List<ArticleFamily> lstArticleFamily = new ArrayList<ArticleFamily>();
        List<Article> lstArticlesleaf = new ArrayList<Article>();
        for (ComponentArticle component : troncoArbol.getAllComponents()) {
            if (component.isComposite()) {
                ArticleComposite artCom = (ArticleComposite) component;
                if (artCom.getReference().equals(reference)) {
                    for (ComponentArticle artComposite : artCom.getAllComponents()) {
                        ArticleLeaf articleLeaf = (ArticleLeaf) artComposite;
                        lstArticlesleaf.add(articleLeaf.getArticle());
                    }
                }
            }
        }
        articleFamiliaOutputDto.setListArticleFamily(lstArticleFamily);
        articleFamiliaOutputDto.setListArticles(lstArticlesleaf);

        return Optional.of(articleFamiliaOutputDto);
    }

    public Optional<List<ArticlesFamiliaOutputDto>> readArticlesFamily(String id) {
        List<ArticlesFamiliaOutputDto> articlesFamiliaOutputDtoList = new ArrayList<>();
        int ini = 0;
        if (!id.equals("root")) {
            ini = Integer.parseInt(id);
        }
        for (int i = ini; i < ini + 5; i++) {
            articlesFamiliaOutputDtoList
                    .add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.ARTICLE, ini + 2 - i));
        }
        for (int i = ini + 5; i < ini + 8; i++) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.ARTICLES, null));
        }
        for (int i = ini + 8; i < ini + 10; i++) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.SIZES, null));
        }

        return Optional.of(articlesFamiliaOutputDtoList);
    }

}
