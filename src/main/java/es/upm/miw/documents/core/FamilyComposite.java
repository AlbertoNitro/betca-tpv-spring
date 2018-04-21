package es.upm.miw.documents.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articlesFamily")
public class FamilyComposite extends ArticlesFamily {

    private String reference;

    private String description;

    @DBRef(lazy = true)
    private List<ArticlesFamily> familyCompositeList;

    public FamilyComposite() {
        super(FamilyType.ARTICLES);
        this.familyCompositeList = new ArrayList<>();
    }

    public FamilyComposite(FamilyType familyType, String reference, String description) {
        super(familyType);
        this.reference = reference;
        this.description = description;
        this.familyCompositeList = new ArrayList<>();
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setDescription(String description) {
        this.description = description;
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
        assert articlesFamilyList != null;
        this.familyCompositeList.add(articlesFamilyList);
    }

    @Override
    public void remove(ArticlesFamily articlesFamilyList) {
        assert articlesFamilyList != null;
        this.familyCompositeList.remove(articlesFamilyList);
    }

    public List<ArticlesFamily> getFamilyCompositeList() {
        return familyCompositeList;
    }

    public void setFamilyCompositeList(List<ArticlesFamily> familyCompositeList) {
        this.familyCompositeList = familyCompositeList;
    }

    @Override
    public List<ArticlesFamily> getArticlesFamilyList() {
        return this.familyCompositeList;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for (ArticlesFamily item : familyCompositeList) {
            list.add("DBref:" + item.getId());
        }
        return "FamilyComposite [" + super.toString() + " reference=" + reference + ", description=" + description + ", articlesFamilyList="
                + list + "]";
    }

    @Override
    public List<String> getArticleIdList() {
        List<String> articleIdList= new ArrayList<>();
        for (ArticlesFamily articlesFamily : this.familyCompositeList) {
            articleIdList.addAll(articlesFamily.getArticleIdList());
        }
        return articleIdList;
    }

}
