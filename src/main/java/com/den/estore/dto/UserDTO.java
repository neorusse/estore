package com.den.estore.dto;

import lombok.Data;

@Data
public class UserDTO {

  private long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
  private String confirmPassword;
  private String encryptedPassword;
}
