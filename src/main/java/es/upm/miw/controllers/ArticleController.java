package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.dtos.ArticleInputDto;
import es.upm.miw.dtos.ArticleOutputDto;
import es.upm.miw.repositories.core.ArticleRepository;

@Controller
public class ArticleController {

	private static final String VARIOUS_CODE = "1";

	@Autowired
	private ArticleRepository articleRepository;

	public Optional<ArticleOutputDto> readArticle(String code) {
		ArticleOutputDto articleOutputDto = this.articleRepository.findMinimumByCode(code);
		if (articleOutputDto == null && code.length() < 5) {
		    try{
		        Double.parseDouble(code);
		        articleOutputDto = this.articleRepository.findMinimumByCode(VARIOUS_CODE);
		    }catch(NumberFormatException nfe) {
		        // Nothing to do
		    }
		}
		if (articleOutputDto == null) {
			return Optional.empty();
		} else {
			return Optional.of(articleOutputDto);
		}
	}

	public ArticleOutputDto postFastArticle(ArticleOutputDto articleOuputDto) {

		Article articulo = new Article(articleOuputDto.getCode(), articleOuputDto.getDescription(),
				articleOuputDto.getRetailPrice()).reference(articleOuputDto.getReference())
						.stock(articleOuputDto.getStock()).build();
		this.articleRepository.save(articulo);
		return articleOuputDto;
	}

	public List<ArticleOutputDto> readAll() {

		List<Article> articleList = this.articleRepository.findAll();
		List<ArticleOutputDto> articleListDto = new ArrayList<ArticleOutputDto>();
		for (Article articulo : articleList) {
			articleListDto.add(new ArticleOutputDto(articulo.getCode(), articulo.getReference(),
					articulo.getDescription(), articulo.getRetailPrice(), articulo.getStock()));
		}
		return articleListDto;

	}

	public List<ArticleOutputDto> readAllIncompletes() {

		List<Article> articleList = this.articleRepository.findAll();
		List<ArticleOutputDto> articleListDto = new ArrayList<ArticleOutputDto>();
		for (Article articulo : articleList) {
			if (articulo.getReference() == null || articulo.getStock() == null) {
				articleListDto.add(new ArticleOutputDto(articulo.getCode(), articulo.getReference(),
						articulo.getDescription(), articulo.getRetailPrice(), articulo.getStock()));
			}
		}
		return articleListDto;

	}
    public List<ArticleOutputDto> readFilterArticle(ArticleInputDto dto) {
    	List<ArticleOutputDto> articleListDto = new ArrayList<ArticleOutputDto>();
    	BigDecimal  bg1 = new BigDecimal("0");

    	if(dto.getReference()==null) {
	    	if(dto.getProvider()=="") {
	    		articleListDto = this.articleRepository.findByDescriptionLike(dto.getDescription());
	    		if(dto.getRetailPriceMax().compareTo(bg1)!=0) {
	        		articleListDto =this.filterRangeRetailPrice(dto,articleListDto);
	    		}else {
		    		if(dto.getDescription()=="") {
		    			articleListDto.clear();
		    		}
	    		}
	    	}else {
	    		articleListDto=this.articleRepository.findByDescriptionProvider(dto.getDescription(),dto.getProvider());
	    		if(dto.getRetailPriceMax().compareTo(bg1)!=0) {
	        		articleListDto=this.filterRangeRetailPrice(dto,articleListDto);
	    		}
	    	}
    	}else {
	    	if(dto.getProvider()=="") {
	    		articleListDto = this.articleRepository.findByReferenceAndDescriptionLike(dto.getReference(),dto.getDescription());
	    		if(dto.getRetailPriceMax().compareTo(bg1)!=0) {
	        		articleListDto =this.filterRangeRetailPrice(dto,articleListDto);
	    		}
	    	}else {
	    		articleListDto=this.articleRepository.findByReferenceDescriptionProvider(dto.getReference(),dto.getDescription(),dto.getProvider());
	    		if(dto.getRetailPriceMax().compareTo(bg1)!=0) {
	        		articleListDto=this.filterRangeRetailPrice(dto,articleListDto);
	    		}
	    	}
    	}
    	return articleListDto;
	}
    
    public void putArticle(String code, ArticleOutputDto articleDto) {
    		Article article = new Article();
    		article.setCode(articleDto.getCode());
    		article.setDescription(articleDto.getDescription());
    		article.setReference(articleDto.getReference());
    		article.setRetailPrice(articleDto.getRetailPrice());
    		article.setStock(articleDto.getStock());
    		this.articleRepository.save(article);
	}
    
    private List<ArticleOutputDto> filterRangeRetailPrice(ArticleInputDto articleInputDto,List<ArticleOutputDto> articleList) {
    	List<ArticleOutputDto> articleListDto = new ArrayList<ArticleOutputDto>();
		for (ArticleOutputDto article : articleList) {
			if(article.getRetailPrice().compareTo(articleInputDto.getRetailPriceMin())==1 && article.getRetailPrice().compareTo(articleInputDto.getRetailPriceMax())==-1 ) {
				articleListDto.add(new ArticleOutputDto(article.getCode(), article.getReference(),
						article.getDescription(), article.getRetailPrice(), article.getStock()));
			}
		}
		return articleListDto;
    }
	
}
