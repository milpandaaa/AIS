package com.ais.project.repositories;

import com.ais.project.models.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Iterable<Article> findAll(Sort sort);
}
