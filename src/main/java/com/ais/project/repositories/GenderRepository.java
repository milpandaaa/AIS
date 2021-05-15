package com.ais.project.repositories;

import com.ais.project.models.Gender;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
}
