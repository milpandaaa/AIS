package com.ais.project.repositories;

import com.ais.project.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {

}
