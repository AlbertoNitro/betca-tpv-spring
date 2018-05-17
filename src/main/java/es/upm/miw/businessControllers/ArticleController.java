package es.upm.miw.businessControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.dataServices.DatabaseSeederService;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.exceptions.FieldAlreadyExistException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    public ArticleDto readArticle(String code) throws NotFoundException {
        return new ArticleDto(this.readOne(code));
    }

    private Article readOne(String code) throws NotFoundException {
        Article article = this.articleRepository.findOne(code);
        if (article == null) {
            throw new NotFoundException("Article code (" + code + ")");
        }
        return article;
    }

    public ArticleDto createArticle(ArticleDto articleDto) throws FieldAlreadyExistException {
        String code = (articleDto.getCode() == null) ? this.databaseSeederService.createEan13() : articleDto.getCode();
        if (this.articleRepository.findOne(code) != null) {
            throw new FieldAlreadyExistException("Article code (" + code + ")");
        }
        int stock = (articleDto.getStock() == null) ? 0 : articleDto.getStock();
        Provider provider = null;
        if (articleDto.getProvider() != null) {
            provider = this.providerRepository.findOne(articleDto.getProvider());
        }
        Article article = Article.builder().code(code).description(articleDto.getDescription()).retailPrice(articleDto.getRetailPrice())
                .reference(articleDto.getReference()).stock(stock).provider(provider).build();
        this.articleRepository.save(article);
        return new ArticleDto(article);
    }

    public void updateArticle(String code, ArticleDto articleDto) throws NotFoundException {
        Article article = readOne(code);
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

    public void updateArticleStock(String code, Integer stock) throws NotFoundException {
        Article article = readOne(code);
        article.setStock(stock);
        this.articleRepository.save(article);
    }

    public List<ArticleDto> findIncompletes() {
        return this.articleRepository
                .findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();
    }

    public List<ArticleDto> findByFieldsWithAnd(String reference, String description, String provider) {
        List<Article> articleList;
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();

        articleList = this.articleRepository.findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider(reference, description,
                provider);

        for (Article article : articleList) {
            articleListDto.add(new ArticleDto(article));
        }

        return articleListDto;

    }

}
