package com.ais.project.repositories;

import com.ais.project.models.Offices;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface OfficeRepository extends CrudRepository<Offices, Integer> {
    Iterable<Offices> findAll(Sort sort);

}
