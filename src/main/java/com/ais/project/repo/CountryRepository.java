package com.ais.project.repo;

import com.ais.project.models.Article;
import com.ais.project.models.Card;
import com.ais.project.models.Countries;
import com.ais.project.models.Names;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Countries, Integer> {
    Iterable<Countries> findAll(Sort sort);
}
