package com.ais.project.repo;

import com.ais.project.models.Punishment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PunishmentRepository extends CrudRepository<Punishment, Integer> {
    Iterable<Punishment> findAll(Sort sort);
}
