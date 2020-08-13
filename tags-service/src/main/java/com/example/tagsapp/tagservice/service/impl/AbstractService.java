package com.example.tagsapp.tagservice.service.impl;

import com.example.tagsapp.tagservice.domain.model.AbstractModel;
import com.example.tagsapp.tagservice.domain.repo.Repository;
import com.example.tagsapp.tagservice.service.Service;

public abstract class AbstractService<M extends AbstractModel, R extends Repository<M>> implements Service<M, R> {

    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }
}
