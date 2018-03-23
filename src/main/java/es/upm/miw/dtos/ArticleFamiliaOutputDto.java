package es.upm.miw.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.dtos.validations.ListNotEmpty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleFamiliaOutputDto {

    @ListNotEmpty
    private List<Article> listArticles;

    @ListNotEmpty
    private List<ArticleFamily> listArticleFamily;

    public ArticleFamiliaOutputDto() {
        // Empty for framework
    }

    public ArticleFamiliaOutputDto(List<Article> listArticles, List<ArticleFamily> listArticleFamily) {
        super();
        this.listArticles = listArticles;
        this.listArticleFamily = listArticleFamily;
    }

    public List<Article> getListArticles() {
        return listArticles;
    }

    public void setListArticles(List<Article> listArticles) {
        this.listArticles = listArticles;
    }

    public List<ArticleFamily> getListArticleFamily() {
        return listArticleFamily;
    }

    public void setListArticleFamily(List<ArticleFamily> listArticleFamily) {
        this.listArticleFamily = listArticleFamily;
    }

    @Override
    public String toString() {
        return "ArticleFamiliaOutputDto [listArticles=" + listArticles + ", listArticleFamily=" + listArticleFamily + "]";
    }

}
