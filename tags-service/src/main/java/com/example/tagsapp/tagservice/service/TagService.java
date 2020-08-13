package com.example.tagsapp.tagservice.service;

import com.example.tagsapp.tagservice.domain.model.Tag;

import java.util.List;

public interface TagService {

    List<Tag> list();

    Tag get(Long id);

    void delete(Long id);

    Tag save(Tag tag);

    Tag update(Long id, Tag incomingTag);

}
