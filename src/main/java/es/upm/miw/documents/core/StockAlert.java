package es.upm.miw.documents.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StockAlert {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String name;
    
    @DBRef
    private List<ArticleAlert> articles;

    public StockAlert() {
        this.articles = new ArrayList<>();
    }

    public StockAlert(String name, List<ArticleAlert> articles) {
        this();
        this.name = name;
        this.articles = articles;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<ArticleAlert> getArticles(){
    		return articles;
    }
    
    public void setArticles(List<ArticleAlert> articles){
		this.articles = articles;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id.equals(((StockAlert) obj).id);
    }

    @Override
    public String toString() {
        return "Stock alert [id=" + id + ", name=" + name + ", articles alert=" + articles.toString() + "]";
    }

}
