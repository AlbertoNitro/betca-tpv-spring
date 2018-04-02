package es.upm.miw.documents.core;

import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class ArticlesFamily {

    @Id
    private String id;

    private FamilyType familyType;

    public ArticlesFamily(FamilyType familyType) {
        this.familyType = familyType;
    }

    public String getId() {
        return id;
    }

    public FamilyType getFamilyType() {
        return familyType;
    }

    public abstract String getReference();

    public abstract String description();

    public abstract Integer stock();

    public abstract void add(ArticlesFamily articlesFamily);

    public abstract void remove(ArticlesFamily articlesFamily);
    
    public abstract List<ArticlesFamily> list();

    @Override
    public String toString() {
        return "ArticlesFamily [id=" + id + ", familyType=" + familyType + "]";
    }

}
