package es.upm.miw.documents.core;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articlesFamily")
public class FamilyArticle extends ArticlesFamily {

    @DBRef
    private Article article;

    public FamilyArticle(Article article) {
        super(FamilyType.ARTICLE);
        this.article = article;
    }

    @Override
    public String getReference() {
        return this.article.getReference();
    }

    @Override
    public String getDescription() {
        return this.article.getDescription();
    }

    @Override
    public Integer getStock() {
        return this.article.getStock();
    }

    @Override
    public void add(ArticlesFamily familyComponent) { 
    }

    @Override
    public void remove(ArticlesFamily familyComponent) { 
    }

    @Override
    public List<ArticlesFamily> getArticlesFamilyList() {
        return null;
    }

    @Override
    public String toString() {
        return "FamilyArticle [article=" + article + ", toString()=" + super.toString() + "]";
    }

}
