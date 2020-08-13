package com.example.tagsapp.tagservice.domain.repo;

import com.example.tagsapp.tagservice.domain.model.AbstractModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<M extends AbstractModel> extends CrudRepository<M, Long> {
}
