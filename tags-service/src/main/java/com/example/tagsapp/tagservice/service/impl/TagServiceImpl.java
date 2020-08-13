package com.example.tagsapp.tagservice.service.impl;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.domain.repo.TagRepository;
import com.example.tagsapp.tagservice.exception.EntityNotFoundException;
import com.example.tagsapp.tagservice.service.TagService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagServiceImpl extends AbstractService<Tag, TagRepository> implements TagService {

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        super(repository);
    }

    @Override
    public List<Tag> list() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Tag get(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Tag save(Tag tag) {
        tag.setId(null);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());
        return repository.save(tag);
    }

    @Override
    public Tag update(Long id, Tag incomingTag) {
        Tag dbTag = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        dbTag.setKey(incomingTag.getKey());
        dbTag.setValue(incomingTag.getValue());
        dbTag.setUpdatedAt(LocalDateTime.now());
        return repository.save(dbTag);
    }
}
