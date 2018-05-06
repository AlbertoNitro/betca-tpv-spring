package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TagController;
import es.upm.miw.dtos.TagDto;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.NotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TagResource.TAGS)
public class TagResource {

    public static final String TAGS = "/tags";

    public static final String ID_ID = "/{id}";

    public static final String STICKER = "/sticker";

    @Autowired
    private TagController tagController;

    @PostMapping
    public void create(@Valid @RequestBody TagDto tagDto) throws NotFoundException {
        this.tagController.create(tagDto);
    }

    @GetMapping(value = ID_ID)
    public TagDto read(@PathVariable String id) throws NotFoundException {
        return this.tagController.read(id).orElseThrow(() -> new NotFoundException("Tag id (" + id + ")"));
    }

    @GetMapping(value = ID_ID + STICKER)
    public byte[] tag24(@PathVariable String id) throws NotFoundException, FileException {
        return this.tagController.tag24(id).orElseThrow(() -> new FileException("Tag pdf exception"));
    }

    @PutMapping(value = ID_ID)
    public void update(@PathVariable String id, @Valid @RequestBody TagDto tagDto) throws NotFoundException {
        this.tagController.update(id, tagDto);
    }

    @GetMapping
    public List<TagDto> findAll() {
        return this.tagController.findAll();
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.tagController.delete(id);
    }

}
