package es.upm.miw.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Tag;
import es.upm.miw.dtos.validations.ListNotEmpty;

public class TagDto {

    private String id;

    @NotNull
    private String description;

    @JsonInclude(Include.NON_NULL)
    @ListNotEmpty
    private List<ArticleMinimumDto> articles;

    public TagDto() {
    }

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.description = tag.getDescription();
        this.articles = new ArrayList<>();
        for (Article article : tag.getArticleList()) {
            articles.add(new ArticleMinimumDto(article));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ArticleMinimumDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleMinimumDto> articlesList) {
        this.articles = articlesList;
    }

    @Override
    public String toString() {
        return "TagDto [id=" + id + ", description=" + description + ", articles=" + articles + "]";
    }

}
