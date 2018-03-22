package es.upm.miw.dtos;

import java.util.List;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import es.upm.miw.dtos.validations.ListNotEmpty;

public class ArticleFamiliaOutputDto {

    @ListNotEmpty
    private List<Article> listArticles;

    @ListNotEmpty
    private List<ArticleFamily> listArticleFamily;

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
