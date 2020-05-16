package com.den.estore.service.impl;

import com.den.estore.io.entity.Cart;
import com.den.estore.io.entity.Item;
import com.den.estore.io.entity.Users;
import com.den.estore.io.repository.CartRepository;
import com.den.estore.io.repository.ItemRepository;
import com.den.estore.io.repository.UserRepository;
import com.den.estore.model.request.CartRequestModel;
import com.den.estore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  CartRepository cartRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ItemRepository itemRepository;

  @Override
  public Cart AddToCart(CartRequestModel cart) {

    Users user = userRepository.findByUsername(cart.getUsername());

    if(user == null) {
      throw new RuntimeException("Not found");
    }

    Optional<Item> item = itemRepository.findById(cart.getItemId());

    if(!item.isPresent()) {
      throw new RuntimeException("Item Not found");
    }

    Cart userCart = user.getCart();

    IntStream.range(0, cart.getQuantity())
      .forEach(i -> userCart.addItem(item.get()));
    cartRepository.save(userCart);

    return userCart;

  }

  @Override
  public Cart RemoveFromCart(CartRequestModel cart) {

    Users user = userRepository.findByUsername(cart.getUsername());

    if(user == null) {
      throw new RuntimeException("Not found");
    }

    Optional<Item> item = itemRepository.findById(cart.getItemId());

    if(!item.isPresent()) {
      throw new RuntimeException("Item Not found");
    }

    Cart userCart = user.getCart();

    IntStream.range(0, cart.getQuantity())
      .forEach(i -> userCart.removeItem(item.get()));
    cartRepository.save(userCart);

    return userCart;

  }

}
