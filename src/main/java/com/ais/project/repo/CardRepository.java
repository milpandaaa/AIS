package com.ais.project.repo;

import com.ais.project.models.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

//    public List<Card> findByGetDateOfInitiation();
}
