package com.ais.project.repositories;

import com.ais.project.models.Names;
import com.ais.project.models.Patronymics;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatronymicRepository extends CrudRepository<Patronymics, Integer> {

    Iterable<Patronymics> findAll(Sort sort);
    List<Patronymics> findByPatronymic(String patronymic);
}
