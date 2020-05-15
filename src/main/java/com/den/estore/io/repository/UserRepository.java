package com.den.estore.io.repository;

import com.den.estore.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);
  UserEntity findByUsername(String username);

}
