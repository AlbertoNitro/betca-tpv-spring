package es.upm.miw.documents.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articlesFamily")
public class FamilyComposite extends ArticlesFamily {

    private String reference;

    private String description;
    
    @DBRef
    private List<ArticlesFamily> articlesFamilyList;

    
    public FamilyComposite(FamilyType familyType, String reference, String description) {
        super(familyType);
        this.reference = reference;
        this.description = description;
        this.articlesFamilyList= new ArrayList<>();
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Integer getStock() {
        return null;
    }

    @Override
    public void add(ArticlesFamily articlesFamilyList) {
        assert articlesFamilyList!=null;
        this.articlesFamilyList.add(articlesFamilyList);
    }

    @Override
    public void remove(ArticlesFamily articlesFamilyList) {
        assert articlesFamilyList!=null;
        this.articlesFamilyList.remove(articlesFamilyList);
    }
    
    public void setArticlesFamilyList(List<ArticlesFamily> articlesFamilyList) {
        this.articlesFamilyList = articlesFamilyList;
    }

    @Override
    public List<ArticlesFamily> getArticlesFamilyList() {
        return this.articlesFamilyList;
    }
    

    @Override
    public String toString() {
        return "FamilyComposite [reference=" + reference + ", description=" + description + ", articlesFamilyList=" + articlesFamilyList
                + ", toString()=" + super.toString() + "]";
    }

}
