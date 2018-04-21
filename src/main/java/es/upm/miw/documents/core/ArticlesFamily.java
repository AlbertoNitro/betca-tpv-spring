package es.upm.miw.documents.core;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articlesFamily")
public abstract class ArticlesFamily {

    @Id
    private String id;

    private FamilyType familyType;

    public ArticlesFamily() {
    }

    public ArticlesFamily(FamilyType familyType) {
        this.familyType = familyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FamilyType getFamilyType() {
        return familyType;
    }

    public void setFamilyType(FamilyType familyType) {
        this.familyType = familyType;
    }

    public abstract String getReference();

    public abstract String getDescription();

    public abstract Integer getStock();

    public abstract void add(ArticlesFamily articlesFamily);

    public abstract void remove(ArticlesFamily articlesFamily);

    public abstract List<ArticlesFamily> getArticlesFamilyList();
    
    public abstract List<String> getArticleIdList();

    
    @Override
    public int hashCode() {
        return this.id.hashCode();
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
        return id.equals(((ArticlesFamily) obj).id);
    }

    @Override
    public String toString() {
        return "ArticlesFamily [id=" + id + ", familyType=" + familyType + "]";
    }

}
