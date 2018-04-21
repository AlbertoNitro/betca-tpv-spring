package es.upm.miw.resources;

import java.util.List;
import java.util.Optional;

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
import es.upm.miw.resources.exceptions.OrderException;
import es.upm.miw.resources.exceptions.TagException;

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
    public void create(@Valid @RequestBody TagDto tagDto) throws TagException {
        Optional<String> error = this.tagController.create(tagDto);
        if (error.isPresent()) {
            throw new TagException(error.get());
        }
    }

    @GetMapping
    public List<TagDto> readAll() {
        return this.tagController.readAll();
    }

    @GetMapping(value = ID_ID)
    public TagDto readOne(@PathVariable String id) throws TagException {
        return this.tagController.readOne(id).orElseThrow(() -> new TagException("Id not found. " + id));
    }

    @GetMapping(value = ID_ID + STICKER)
    public byte[] tag24(@PathVariable String id) throws TagException {
        return this.tagController.tag24(id).orElseThrow(() -> new TagException("Id not found. " + id));
    }

    @PutMapping(value = ID_ID)
    public void update(@PathVariable String id, @Valid @RequestBody TagDto tagDto) throws OrderException {
        Optional<String> error = this.tagController.update(id, tagDto);
        if (error.isPresent()) {
            throw new OrderException(error.get());
        }
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) throws TagException {
        this.tagController.delete(id);
    }

}
