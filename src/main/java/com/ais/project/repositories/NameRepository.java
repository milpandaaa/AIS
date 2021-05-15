package com.ais.project.repositories;

import com.ais.project.models.Names;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<Names, Integer> {

    Iterable<Names> findAll(Sort sort);
}
