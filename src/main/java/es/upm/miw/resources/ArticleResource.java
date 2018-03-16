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
import es.upm.miw.documents.core.Role;
import es.upm.miw.dtos.ArticleOutputDto;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";
    
    public static final String INCOMPLETES = "/incompletos";

    public static final String CODE_ID = "/{code}";

    public static final String FILTER ="/filter";

    @Autowired
    private ArticleController articleController;

    @GetMapping(value = CODE_ID)
    public ArticleOutputDto readArticle(@PathVariable String code) throws ArticleCodeNotFoundException {
        return this.articleController.readArticle(code).orElseThrow(() -> new ArticleCodeNotFoundException(code));
    }
    
	@RequestMapping(method = RequestMethod.POST)
	public ArticleOutputDto postArticle(@RequestBody ArticleOutputDto articleOuputDto) {
		return this.articleController.postFastArticle(articleOuputDto);

	}
	
	@RequestMapping(method = RequestMethod.GET)
    public List<ArticleOutputDto> readAllArticles() {
        return this.articleController.readAll();
    }
	
    @GetMapping(value = INCOMPLETES)
    public List<ArticleOutputDto> readAllArticlesIncompletes(){
        return this.articleController.readAllIncompletes();
    }
    @RequestMapping(value = FILTER , method = RequestMethod.POST)
    public List<ArticleOutputDto> readFilterArticle(@RequestBody ArticleOutputDto dto){
        return this.articleController.readFilterArticle(dto);
    }
    
    @PutMapping(value = CODE_ID)
    public void putCustomer(@PathVariable String code, @Valid @RequestBody ArticleOutputDto articleDto) {
    		this.articleController.putArticle(code,articleDto);
    }
}
