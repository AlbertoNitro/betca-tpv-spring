package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class ArticleController {

    private static final String VARIOUS_CODE = "1";    
    private static final String VARIOUS_NAME = "Varios";
    private static final BigDecimal VARIOUS_STOCK = new BigDecimal("100.00");

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
                if (articleDto == null) { // SOLO ocurre una vez, después de vaciar la BD
                    Provider provider = new Provider(VARIOUS_NAME, null, null, null, null, null);
                    article = new Article(VARIOUS_CODE, VARIOUS_NAME, VARIOUS_STOCK, VARIOUS_NAME, 1000, provider, true);
                    this.providerRepository.save(provider);
                    this.articleRepository.save(article);
                    articleDto = this.articleRepository.findMinimumByCode(VARIOUS_CODE);
                }
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
        return this.articleRepository
                .findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();
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

    public List<ArticleDto> find(String reference, String description, String provider) {
        List<Article> articleList;
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();
        if (provider == null) {
            articleList = this.articleRepository.findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCase(reference, description);
        } else {
            articleList = this.articleRepository.findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider(reference,
                    description, provider);
        }
        for (Article article : articleList) {
            articleListDto.add(new ArticleDto(article));
        }
        return articleListDto;
    }

}
