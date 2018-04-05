package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticlesFamily;
import es.upm.miw.documents.core.FamilyArticle;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.ArticlesFamilyDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ArticlesFamilyRepository;
import es.upm.miw.repositories.core.FamilyArticleRepository;

@Controller
public class ArticlesFamilyController {

    private static final String ROOT = "root";

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    @Autowired
    private FamilyArticleRepository familyArticleRepository;

    @Autowired
    private ArticleRepository articlesRepository;

    public Optional<List<ArticlesFamilyDto>> readArticlesFamily(String id) {
        List<ArticlesFamilyDto> articlesFamiliaOutputDtoList = new ArrayList<>();

        ArticlesFamily articlesFamily = this.articlesFamilyRepository.findOne(id);
        if (articlesFamily == null && ROOT.equals(id)) { // Solo ocurre una vez en deploy con BD vacias
            ArticlesFamily root = new FamilyComposite(FamilyType.ARTICLES, ROOT, ROOT);
            root.setId(ROOT);
            this.articlesFamilyRepository.save(root);
            articlesFamily = root;
        }
        if (articlesFamily == null) {
            return Optional.empty();
        }
        for (ArticlesFamily articlesFamilyIn : articlesFamily.getArticlesFamilyList()) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamilyDto(articlesFamilyIn));
        }
        return Optional.of(articlesFamiliaOutputDtoList);
    }

    public List<ArticlesFamilyDto> readAll() {
        List<ArticlesFamily> articlesFamilyList = this.articlesFamilyRepository.findAll();
        List<ArticlesFamilyDto> articlesFamiliaOutputDtoList = new ArrayList<>();

        for (ArticlesFamily articlesFamily : articlesFamilyList) {
            if (!ROOT.equals(articlesFamily.getId())) {
                ArticlesFamilyDto articlesFamilyDto = new ArticlesFamilyDto(articlesFamily.getId(), articlesFamily.getDescription());
                articlesFamilyDto.setFamilyType(articlesFamily.getFamilyType());
                articlesFamiliaOutputDtoList.add(articlesFamilyDto);
            }
        }
        return articlesFamiliaOutputDtoList;
    }

    public Optional<String> create(ArticlesFamilyDto articlesFamilyDto) {
        if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLE)) {
            Article article = this.articlesRepository.findOne(articlesFamilyDto.getArticleId());
            if (article == null) {
                return Optional.of("Article id not found. " + articlesFamilyDto.getArticleId());
            }
            this.articlesFamilyRepository.save(new FamilyArticle(article));
        } else if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLES)
                || articlesFamilyDto.getFamilyType().equals(FamilyType.SIZES)) {
            this.articlesFamilyRepository.save(new FamilyComposite(articlesFamilyDto.getFamilyType(), articlesFamilyDto.getReference(),
                    articlesFamilyDto.getDescription()));
        }
        return Optional.empty();
    }

    public Optional<String> addChild(String id, String childId) {
        ArticlesFamily family = this.articlesFamilyRepository.findOne(id);
        if (family == null) {
            Optional.of("family Id not found. " + id);
        }
        if (family.getFamilyType().equals(FamilyType.ARTICLE)) {
            Optional.of("family Id does not allow to add. " + id);
        }
        ArticlesFamily familyChild = this.articlesFamilyRepository.findOne(childId);
        if (familyChild == null) {
            Optional.of("family Id not found. " + familyChild);
        }
        if (family.getFamilyType().equals(FamilyType.SIZES) && !familyChild.getFamilyType().equals(FamilyType.ARTICLE)) {
            Optional.of("family Id does not allow to add another family. " + id + "->" + familyChild);
        }

        family.getArticlesFamilyList().add(familyChild);
        this.articlesFamilyRepository.save(family);
        return Optional.empty();
    }

    public Optional<String> deleteChild(String id, String childId) {
        ArticlesFamily family = this.articlesFamilyRepository.findOne(id);
        if (family == null) {
            Optional.of("family Id not found. " + id);
        }
        if (family.getFamilyType().equals(FamilyType.ARTICLE)) {
            Optional.of("family Id does not allow to delete. " + id);
        }
        ArticlesFamily familyChild = this.articlesFamilyRepository.findOne(childId);
        if (familyChild == null) {
            Optional.of("family Id not found. " + familyChild);
        }
        if (family.getFamilyType().equals(FamilyType.SIZES) && !familyChild.getFamilyType().equals(FamilyType.ARTICLE)) {
            Optional.of("family Id does not allow to delete another family. " + id + "->" + familyChild);
        }

        family.getArticlesFamilyList().remove(familyChild);
        this.articlesFamilyRepository.save(family);
        System.out.println("despues de eliminar..." + family);
        return Optional.empty();
    }

    public Optional<ArticleDto> readIdArticle(String id) {
        FamilyArticle familyArticle = this.familyArticleRepository.findOne(id);
        if (familyArticle == null || !familyArticle.getFamilyType().equals(FamilyType.ARTICLE)) {
            Optional.empty();
        }
        return Optional.of(new ArticleDto(familyArticle.getArticle()));
    }

}
