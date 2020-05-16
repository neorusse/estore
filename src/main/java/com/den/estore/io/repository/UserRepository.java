package com.den.estore.io.repository;

import com.den.estore.io.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {

  Users findByEmail(String email);
  Users findByUsername(String username);

}
