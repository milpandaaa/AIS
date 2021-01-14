package com.ais.project.repo;

import com.ais.project.models.Gender;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
}
