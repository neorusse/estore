package com.den.estore.model.response;

import lombok.Data;

@Data
public class UserRes {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String username;

}
