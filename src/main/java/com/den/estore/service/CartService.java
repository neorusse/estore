package com.den.estore.service;

import com.den.estore.io.entity.Cart;
import com.den.estore.model.request.CartRequestModel;

public interface CartService {

  Cart AddToCart(CartRequestModel cart);

  Cart RemoveFromCart(CartRequestModel cart);
}
