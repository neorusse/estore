package com.den.estore.controllers;

import com.den.estore.io.entity.UserOrder;
import com.den.estore.io.entity.Users;
import com.den.estore.io.repository.OrderRepository;
import com.den.estore.io.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  // adding logs
  private final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OrderRepository orderRepository;


  @PostMapping("/submit/{username}")
  public ResponseEntity<UserOrder> submit(@PathVariable String username) {

    logger.info("HTTP POST request received at /api/order/submit/{username} url");

    Users user = userRepository.findByUsername(username);

    if(user == null) {
      return ResponseEntity.notFound().build();
    }

    UserOrder order = UserOrder.createFromCart(user.getCart());
    orderRepository.save(order);

    return ResponseEntity.ok(order);

  }

  @GetMapping("/history/{username}")
  public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {

    logger.info("HTTP GET request received at /api/order/history/{username} url");

    Users user = userRepository.findByUsername(username);

    if(user == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(orderRepository.findByUser(user));

  }

}
