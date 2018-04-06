package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticleAlertController;
import es.upm.miw.dtos.ArticleAlertDto;
import es.upm.miw.resources.exceptions.ArticleAlertCodeNotFoundException;
import es.upm.miw.resources.exceptions.ForbiddenException;

@RestController
@RequestMapping(ArticleAlertResource.ARTICLEALERT)
public class ArticleAlertResource {

	public static final String ARTICLEALERT = "/articleAlert";

    public static final String CODE_ID = "/{code}";
    
    @Autowired
    private ArticleAlertController articleAlertController;

    @PostMapping
    public void createArticleAlert(@Valid @RequestBody ArticleAlertDto articleAlertDto)  {
        this.articleAlertController.createArticleAlert(articleAlertDto);
    }

    @PutMapping(value = CODE_ID)
    public void putArticleAlert(@PathVariable String code, @Valid @RequestBody ArticleAlertDto articleAlertDto)
            throws ForbiddenException {
        if (!this.articleAlertController.putArticleAlert(code, articleAlertDto)) {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping(value = CODE_ID)
    public void deleteArticleAlert(@PathVariable String code) throws ForbiddenException {
        if (!this.articleAlertController.deleteArticleAlert(code)) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = CODE_ID, method = RequestMethod.GET)
    public ArticleAlertDto readStockAlert(@PathVariable String code) throws ArticleAlertCodeNotFoundException {
        return this.articleAlertController.readArticleAlert(code).orElseThrow(() -> new ArticleAlertCodeNotFoundException(code));
    }

    @GetMapping
    public List<ArticleAlertDto> readArticleAlertAll() {
        return this.articleAlertController.readArticleAlertAll();
    }
}
