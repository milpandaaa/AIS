package com.ais.project.repo;

import com.ais.project.models.Article;
import com.ais.project.models.Names;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Iterable<Article> findAll(Sort sort);
}
