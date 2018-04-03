package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ArticleInputDto;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class ArticleController {

    private static final String VARIOUS_CODE = "1";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public Optional<ArticleDto> readArticle(String code) {
        ArticleDto articleDto = null;
        Article article = this.articleRepository.findOne(code);
        if (article != null) {
            articleDto = new ArticleDto(article);
        }
        if (articleDto == null && code.length() < 5) {
            try {
                Double.parseDouble(code);
                articleDto = this.articleRepository.findMinimumByCode(VARIOUS_CODE);
            } catch (NumberFormatException nfe) {
                // Nothing to do
            }
        }
        if (articleDto == null) {
            return Optional.empty();
        } else {
            return Optional.of(articleDto);
        }
    }

    public ArticleDto createArticle(ArticleDto articleOuputDto) {
        Provider provider = null;
        if (articleOuputDto.getProvider() != null) {
            provider = this.providerRepository.findOne(articleOuputDto.getProvider());
        }
        Article articulo = new Article(articleOuputDto.getCode(), articleOuputDto.getDescription(), articleOuputDto.getRetailPrice(),
                articleOuputDto.getReference(), articleOuputDto.getStock(), provider, articleOuputDto.getDiscontinued());
        this.articleRepository.save(articulo);
        return articleOuputDto;
    }

    public List<ArticleDto> readMinimumAll() {
        List<Article> articleList = this.articleRepository.findAll();
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();
        for (Article articulo : articleList) {
            articleListDto.add(new ArticleDto(articulo.getCode(), articulo.getDescription()));
        }
        return articleListDto;
    }

    public List<ArticleDto> readMinimumAllIncompletes() {
        List<Article> articleList = this.articleRepository.findAll();
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();
        for (Article articulo : articleList) {
            if (articulo.getReference() == null || articulo.getStock() == null || articulo.getProvider() == null) {
                articleListDto.add(new ArticleDto(articulo.getCode(), articulo.getDescription()));
            }
        }
        return articleListDto;
    }

    public void putArticle(String code, ArticleDto articleDto) {
        Article article = this.articleRepository.findOne(code);
        assert article != null;
        article.setDescription(articleDto.getDescription());
        article.setReference(articleDto.getReference());
        article.setRetailPrice(articleDto.getRetailPrice());
        article.setStock(articleDto.getStock());
        Provider provider = null;
        if (articleDto.getProvider() != null) {
            provider = this.providerRepository.findOne(articleDto.getProvider());
        }
        article.setProvider(provider);
        this.articleRepository.save(article);
    }

    public List<ArticleDto> readFilterArticle(ArticleInputDto dto) {
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();
        BigDecimal bg1 = new BigDecimal("0");

        if (dto.getReference() == null) {
            if (dto.getProvider() == "") {
                articleListDto = this.articleRepository.findByDescriptionLike(dto.getDescription());
                if (dto.getRetailPriceMax().compareTo(bg1) != 0) {
                    articleListDto = this.filterRangeRetailPrice(dto, articleListDto);
                } else {
                    if (dto.getDescription() == "") {
                        articleListDto.clear();
                    }
                }
            } else {
                articleListDto = this.articleRepository.findByDescriptionProvider(dto.getDescription(), dto.getProvider());
                if (dto.getRetailPriceMax().compareTo(bg1) != 0) {
                    articleListDto = this.filterRangeRetailPrice(dto, articleListDto);
                }
            }
        } else {
            if (dto.getProvider() == "") {
                articleListDto = this.articleRepository.findByReferenceAndDescriptionLike(dto.getReference(), dto.getDescription());
                if (dto.getRetailPriceMax().compareTo(bg1) != 0) {
                    articleListDto = this.filterRangeRetailPrice(dto, articleListDto);
                }
            } else {
                articleListDto = this.articleRepository.findByReferenceDescriptionProvider(dto.getReference(), dto.getDescription(),
                        dto.getProvider());
                if (dto.getRetailPriceMax().compareTo(bg1) != 0) {
                    articleListDto = this.filterRangeRetailPrice(dto, articleListDto);
                }
            }
        }
        return articleListDto;
    }

    private List<ArticleDto> filterRangeRetailPrice(ArticleInputDto articleInputDto, List<ArticleDto> articleList) {
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();
        for (ArticleDto article : articleList) {
            if (article.getRetailPrice().compareTo(articleInputDto.getRetailPriceMin()) == 1
                    && article.getRetailPrice().compareTo(articleInputDto.getRetailPriceMax()) == -1) {
                articleListDto.add(new ArticleDto(article.getCode(), article.getDescription(), article.getReference(),
                        article.getRetailPrice(), article.getStock()));
            }
        }
        return articleListDto;
    }

}
