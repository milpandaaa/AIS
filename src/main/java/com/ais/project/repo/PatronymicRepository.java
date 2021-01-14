package com.ais.project.repo;

import com.ais.project.models.Names;
import com.ais.project.models.Patronymics;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PatronymicRepository extends CrudRepository<Patronymics, Integer> {
    Iterable<Patronymics> findAll(Sort sort);
}
