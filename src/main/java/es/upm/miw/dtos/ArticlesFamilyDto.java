package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.ArticlesFamily;
import es.upm.miw.documents.core.FamilyType;

@JsonInclude(Include.NON_NULL)
public class ArticlesFamilyDto {

    private String id;

    private String reference;

    private String description;

    @NotNull
    private FamilyType familyType;

    private String articleId;

    private Integer stock;

    public ArticlesFamilyDto() {
    }

    public ArticlesFamilyDto(String id, String description) {
        this(id, null, description, null, null);
    }

    public ArticlesFamilyDto(String id, String reference, String description, FamilyType familyType, Integer stock) {
        this.id = id;
        this.reference = reference;
        this.description = description;
        this.familyType = familyType;
        this.stock = stock;
    }

    public ArticlesFamilyDto(ArticlesFamily articlesFamilyIn) {
        this(articlesFamilyIn.getId(), articlesFamilyIn.getReference(), articlesFamilyIn.getDescription(), articlesFamilyIn.getFamilyType(),
                articlesFamilyIn.getStock());
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

    public FamilyType getFamilyType() {
        return this.familyType;
    }

    public void setFamilyType(FamilyType familyType) {
        this.familyType = familyType;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "ArticlesFamilyDto [id=" + id + ", reference=" + reference + ", description=" + description + ", familyType=" + familyType
                + ", articleId=" + articleId + ", stock=" + stock + "]";
    }

}
