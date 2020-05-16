package com.den.estore.io.repository;

import com.den.estore.io.entity.UserOrder;
import com.den.estore.io.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {

  List<UserOrder> findByUser(Users user);
}
