package es.upm.miw.resources;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleController;
import es.upm.miw.dtos.ArticleInputDto;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";
    
    public static final String INCOMPLETES = "/incompletes";

    public static final String CODE_ID = "/{code}";

    public static final String FILTER ="/filter";

    @Autowired
    private ArticleController articleController;

    @GetMapping(value = CODE_ID)
    public ArticleDto readArticle(@PathVariable String code) throws ArticleCodeNotFoundException {
        return this.articleController.readArticle(code).orElseThrow(() -> new ArticleCodeNotFoundException(code));
    }
    
	@RequestMapping(method = RequestMethod.POST)
	public ArticleDto postArticle(@RequestBody ArticleDto articleOuputDto) {
		return this.articleController.postFastArticle(articleOuputDto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public List<ArticleDto> readAllArticles() {
        return this.articleController.readMinimumAll();
    }
	
    @GetMapping(value = INCOMPLETES)
    public List<ArticleDto> readAllArticlesIncompletes(){
        return this.articleController.readMinimumAllIncompletes();
    }
    @RequestMapping(value = FILTER , method = RequestMethod.POST)
	public List<ArticleDto> readFilterArticle(@RequestBody ArticleInputDto dto){
	    return this.articleController.readFilterArticle(dto);
	}
    
    @PutMapping(value = CODE_ID)
    public void putArticle(@PathVariable String code, @Valid @RequestBody ArticleDto articleDto) {
    		this.articleController.putArticle(code,articleDto);
    }
}
