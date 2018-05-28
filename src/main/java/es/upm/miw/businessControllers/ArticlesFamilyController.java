package es.upm.miw.businessControllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticlesFamily;
import es.upm.miw.documents.core.FamilyArticle;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.dtos.ArticlesFamilyDto;
import es.upm.miw.exceptions.BadRequestException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ArticlesFamilyRepository;
import es.upm.miw.repositories.core.FamilyArticleRepository;
import es.upm.miw.repositories.core.FamilyCompositeRepository;

@Controller
public class ArticlesFamilyController {

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    @Autowired
    private FamilyArticleRepository familyArticleRepository;

    @Autowired
    private FamilyCompositeRepository familyCompositeRepository;

    @Autowired
    private ArticleRepository articlesRepository;

    public List<ArticlesFamilyDto> readArticlesFamily(String id) throws NotFoundException {
        List<ArticlesFamilyDto> articlesFamiliaOutputDtoList = new ArrayList<>();

        ArticlesFamily articlesFamily = findById(id);
        for (ArticlesFamily articlesFamilyIn : articlesFamily.getArticlesFamilyList()) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamilyDto(articlesFamilyIn));
        }
        if (FamilyType.ARTICLES.equals(articlesFamily.getFamilyType())) {
            Collections.sort(articlesFamiliaOutputDtoList, new Comparator<ArticlesFamilyDto>() {
                @Override
                public int compare(ArticlesFamilyDto dto1, ArticlesFamilyDto dto2) {
                    return dto1.getReference().compareTo(dto2.getReference());
                }
            });
        }
        return articlesFamiliaOutputDtoList;
    }

    private ArticlesFamily findById(String id) throws NotFoundException {
        ArticlesFamily articlesFamily = this.articlesFamilyRepository.findOne(id);
        if (articlesFamily == null) {
            throw new NotFoundException("Articles Family (" + id + ")");
        }
        return articlesFamily;
    }

    public List<ArticlesFamilyDto> readAll() {
        List<ArticlesFamily> articlesFamilyList = this.articlesFamilyRepository.findAll();
        List<ArticlesFamilyDto> articlesFamiliaOutputDtoList = new ArrayList<>();

        for (ArticlesFamily articlesFamily : articlesFamilyList) {
            ArticlesFamilyDto articlesFamilyDto = new ArticlesFamilyDto(articlesFamily.getId(), articlesFamily.getDescription());
            articlesFamilyDto.setFamilyType(articlesFamily.getFamilyType());
            articlesFamiliaOutputDtoList.add(articlesFamilyDto);
        }
        return articlesFamiliaOutputDtoList;
    }

    public void create(ArticlesFamilyDto articlesFamilyDto) throws NotFoundException {
        if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLE)) {
            Article article = this.articlesRepository.findOne(articlesFamilyDto.getArticleId());
            if (article == null) {
                throw new NotFoundException("Articles Family (" + articlesFamilyDto.getArticleId() + ")");
            }
            this.articlesFamilyRepository.save(new FamilyArticle(article));
        } else if (articlesFamilyDto.getFamilyType().equals(FamilyType.ARTICLES)
                || articlesFamilyDto.getFamilyType().equals(FamilyType.SIZES)) {
            this.articlesFamilyRepository.save(new FamilyComposite(articlesFamilyDto.getFamilyType(), articlesFamilyDto.getReference(),
                    articlesFamilyDto.getDescription()));
        }
    }

    public void addChild(String id, String childId) throws NotFoundException, BadRequestException {
        ArticlesFamily family = this.findById(id);
        if (family.getFamilyType().equals(FamilyType.ARTICLE)) {
            throw new BadRequestException("family ARTICLE, does not allow to add. " + id);
        }
        ArticlesFamily familyChild = this.findById(childId);
        if (family.getFamilyType().equals(FamilyType.SIZES) && !familyChild.getFamilyType().equals(FamilyType.ARTICLE)) {
            throw new BadRequestException("family SIZES, does not allow to add another family ARTICLES or SIZES. " + id + "->" + familyChild);
        }
        family.getArticlesFamilyList().add(familyChild);
        this.articlesFamilyRepository.save(family);
    }

    public void deleteChild(String id, String childId) throws NotFoundException, BadRequestException {
        ArticlesFamily family = this.findById(id);
        if (family.getFamilyType().equals(FamilyType.ARTICLE)) {
            throw new BadRequestException("family ARTICLE does not allow to delete a child. " + id);
        }
        ArticlesFamily familyChild = this.findById(childId);
        if (family.getFamilyType().equals(FamilyType.SIZES) && !familyChild.getFamilyType().equals(FamilyType.ARTICLE)) {
            throw new BadRequestException("family SIZES does not allow to delete another family. " + id + "->" + familyChild);
        }

        family.getArticlesFamilyList().remove(familyChild);
        this.articlesFamilyRepository.save(family);
    }

    public ArticleDto readIdArticle(String id) throws NotFoundException {
        FamilyArticle familyArticle = this.familyArticleRepository.findOne(id);
        if (familyArticle == null || !familyArticle.getFamilyType().equals(FamilyType.ARTICLE)) {
            throw new NotFoundException("Articles Family (" + id + ")");
        }
        return new ArticleDto(familyArticle.getArticle());
    }

    public void updateReferenceAndDescription(String id, ArticlesFamilyDto articlesFamilyDto) throws NotFoundException {
        FamilyComposite family = this.familyCompositeRepository.findOne(id);
        if (family == null) {
            throw new NotFoundException("family ARTICLES or SIZES not found (" + id + ")");
        }
        family.setReference(articlesFamilyDto.getReference());
        family.setDescription(articlesFamilyDto.getDescription());
        this.familyCompositeRepository.save(family);
    }

}
