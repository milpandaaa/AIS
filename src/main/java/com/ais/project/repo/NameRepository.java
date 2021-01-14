package com.ais.project.repo;

import com.ais.project.models.Card;
import com.ais.project.models.Names;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NameRepository extends CrudRepository<Names, Integer> {

    Iterable<Names> findAll(Sort sort);
}
