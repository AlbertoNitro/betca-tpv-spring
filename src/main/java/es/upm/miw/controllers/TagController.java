package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Tag;
import es.upm.miw.dtos.ArticleMinimumDto;
import es.upm.miw.dtos.TagDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.TagRepository;
import es.upm.miw.services.PdfService;

@Controller
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PdfService pdfService;

    public Optional<String> create(TagDto tagDto) {
        return this.save(tagDto, new Tag());
    }

    public Optional<String> save(TagDto tagDto, Tag tag) {
        List<Article> articleList = new ArrayList<>();
        for (ArticleMinimumDto articleDto : tagDto.getArticles()) {
            Article article = this.articleRepository.findOne(articleDto.getCode());
            if (article == null) {
                return Optional.of("Article not found. " + articleDto.getCode());
            }
            articleList.add(article);
        }
        tag.setDescription(tagDto.getDescription());
        tag.setArticleList(articleList);
        this.tagRepository.save(tag);
        return Optional.empty();
    }

    public List<TagDto> readAll() {
        List<TagDto> tagDtoList = new ArrayList<>();
        List<Tag> tagList = this.tagRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        for (Tag tag : tagList) {
            tagDtoList.add(new TagDto(tag));
        }
        return tagDtoList;
    }

    public Optional<TagDto> readOne(String id) {
        Tag tag = this.tagRepository.findOne(id);
        if (tag == null) {
            return Optional.empty();
        }
        return Optional.of(new TagDto(tag));
    }

    public Optional<String> update(String id, TagDto tagDto) {
        Tag tag = this.tagRepository.findOne(id);
        if (tag == null) {
            return Optional.of("Tag not found. " + id);
        }
        return this.save(tagDto, tag);
    }

    public void delete(String id) {
        Tag tag = this.tagRepository.findOne(id);
        if (tag != null) {
            this.tagRepository.delete(id);
        }
    }

    public Optional<byte[]> tag24(String id) {
        Tag tag = this.tagRepository.findOne(id);
        if (tag == null) {
            return Optional.empty();
        }
        return this.pdfService.generateLabels24(tag.getArticleList());
    }

}
