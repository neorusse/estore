package com.den.estore.model.request;

import lombok.Data;

@Data
public class CartRequestModel {

  private String username;
  private long itemId;
  private int quantity;

}
