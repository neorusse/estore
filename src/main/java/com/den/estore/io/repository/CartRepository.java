package com.den.estore.io.repository;

import com.den.estore.io.entity.Cart;
import com.den.estore.io.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Cart findByUser(Users user);
}