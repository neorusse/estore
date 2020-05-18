package com.den.estore.controllers;

import com.den.estore.io.entity.Cart;
import com.den.estore.model.request.CartRequestModel;
import com.den.estore.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  // adding logs
  private final Logger logger = LoggerFactory.getLogger(CartController.class);

  @Autowired
  CartService cartService;

  @PostMapping("/addToCart")
  public ResponseEntity<Cart> addTocart(@RequestBody CartRequestModel cartDetails) {

    logger.info("HTTP POST request received at /api/cart/addToCart url");

    Cart createdCart = cartService.AddToCart(cartDetails);

    return ResponseEntity.ok(createdCart);

  }

  @PostMapping("/removeFromCart")
  public ResponseEntity<Cart> removeFromCart(@RequestBody CartRequestModel cartDetails) {

    logger.info("HTTP POST request received at /api/cart/removeFromCart url");

    Cart createdCart = cartService.RemoveFromCart(cartDetails);

    return ResponseEntity.ok(createdCart);

  }

}
