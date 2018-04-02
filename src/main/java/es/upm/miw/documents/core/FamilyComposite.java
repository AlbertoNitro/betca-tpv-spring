package es.upm.miw.documents.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "familyComponent")
public class FamilyComposite extends ArticlesFamily {

    private String reference;

    private String description;
    
    private List<ArticlesFamily> familyComponentList;

    public FamilyComposite(FamilyType familyType, String reference, String description) {
        super(familyType);
        this.reference = reference;
        this.description = description;
        this.familyComponentList= new ArrayList<>();
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public Integer stock() {
        return null;
    }

    @Override
    public void add(ArticlesFamily familyComponent) {
        assert familyComponent!=null;
        this.familyComponentList.add(familyComponent);
    }

    @Override
    public void remove(ArticlesFamily familyComponent) {
        assert familyComponent!=null;
        this.familyComponentList.remove(familyComponent);
    }

    @Override
    public List<ArticlesFamily> list() {
        return this.familyComponentList;
    }

    @Override
    public String toString() {
        return "FamilyComposite [reference=" + reference + ", description=" + description + ", familyComponentList=" + familyComponentList
                + ", toString()=" + super.toString() + "]";
    }

}
