package com.example.tagsapp.tagservice.rest;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.rest.dto.TagDto;
import com.example.tagsapp.tagservice.rest.dto.mapper.TagMapper;
import com.example.tagsapp.tagservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TagsController {

    private final TagService service;
    private final TagMapper mapper;

    @Autowired
    public TagsController(TagService service, TagMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = {"/", ""}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> list() {
        return ResponseEntity.ok().body(service.list());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Tag> save(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok().body(service.save(mapper.fromDto(tagDto)));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody TagDto incomingTag) {
        return ResponseEntity.ok().body(service.update(id, mapper.fromDto(incomingTag)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }

}
