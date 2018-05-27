package es.upm.miw.businessControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import es.upm.miw.businessServices.PdfService;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Tag;
import es.upm.miw.dtos.ArticleMinimumDto;
import es.upm.miw.dtos.TagDto;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.TagRepository;

@Controller
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PdfService pdfService;

    public void create(TagDto tagDto) throws NotFoundException {
        this.save(tagDto, new Tag());
    }

    public void save(TagDto tagDto, Tag tag) throws NotFoundException {
        List<Article> articleList = new ArrayList<>();
        for (ArticleMinimumDto articleDto : tagDto.getArticles()) {
            Article article = this.articleRepository.findOne(articleDto.getCode());
            if (article == null) {
                throw new NotFoundException("Article code (" + articleDto.getCode() + ")");
            }
            articleList.add(article);
        }
        tag.setDescription(tagDto.getDescription());
        tag.setArticleList(articleList);
        this.tagRepository.save(tag);
    }

    public TagDto read(String id) throws NotFoundException {
        return new TagDto(readOne(id));
    }

    private Tag readOne(String id) throws NotFoundException {
        Tag tag = this.tagRepository.findOne(id);
        if (tag == null) {
            throw new NotFoundException("Tag id (" + id + ")");
        }
        return tag;
    }

    public Optional<byte[]> tag24(String id) throws NotFoundException {
        Tag tag = readOne(id);
        return this.pdfService.generateLabels24(tag.getArticleList());
    }

    public void update(String id, TagDto tagDto) throws NotFoundException {
        Tag tag = readOne(id);
        this.save(tagDto, tag);
    }

    public void delete(String id) {
        Tag tag = this.tagRepository.findOne(id);
        if (tag != null) {
            this.tagRepository.delete(id);
        }
    }

    public List<TagDto> findAll() {
        List<TagDto> tagDtoList = new ArrayList<>();
        List<Tag> tagList = this.tagRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        for (Tag tag : tagList) {
            tagDtoList.add(new TagDto(tag));
        }
        return tagDtoList;
    }

}
