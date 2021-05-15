package com.ais.project.repositories;

import com.ais.project.models.Patronymics;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PatronymicRepository extends CrudRepository<Patronymics, Integer> {
    Iterable<Patronymics> findAll(Sort sort);
}
