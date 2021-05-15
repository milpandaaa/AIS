package com.ais.project.repositories;

import com.ais.project.models.Countries;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Countries, Integer> {
    Iterable<Countries> findAll(Sort sort);
}
