package com.den.estore.model.request;

import lombok.Data;

@Data
public class UserSignUpRequestModel {

  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
  private String confirmPassword;

}
