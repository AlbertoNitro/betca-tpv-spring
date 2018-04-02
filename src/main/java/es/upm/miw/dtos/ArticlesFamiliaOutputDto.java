package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.FamilyType;

public class ArticlesFamiliaOutputDto {

    private String id;

    private String reference;

    private String description;

    private FamilyType composite;
    
    @JsonInclude(Include.NON_NULL)
    private Integer stock;

    public ArticlesFamiliaOutputDto() {
    }

    public ArticlesFamiliaOutputDto(String id, String reference, String description, FamilyType composite, Integer stock) {
        this.id = id;
        this.reference = reference;
        this.description = description;
        this.composite = composite;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FamilyType getComposite() {
        return composite;
    }

    public void setComposite(FamilyType composite) {
        this.composite = composite;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ArticlesFamiliaOutputDto [id=" + id + ", reference=" + reference + ", description=" + description + ", composite="
                + composite + ", stock=" + stock + "]";
    }

}
