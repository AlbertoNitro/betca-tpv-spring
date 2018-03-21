package es.upm.miw.documents.core;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ArticleFamily {
    @Id
    private String id;

    private String reference;

    @DBRef
    private List<Article> articles;

    public ArticleFamily(String reference, List<Article> articles) {
        super();
        this.reference = reference;
        this.articles = articles;
    }

    public String getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /***
     * La clase debería implementar los métodos: hashCode y equals
     */

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            ArticleFamily other = (ArticleFamily) obj;
            return reference.equals(other.reference);
        }
    }

    public ArticleFamily build() {
        return this;
    }

    @Override
    public String toString() {
        return "ArticleFamily [id=" + id + ", reference=" + reference + "]";
    }

}
