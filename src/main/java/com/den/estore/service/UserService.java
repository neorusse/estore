package com.den.estore.service;

import com.den.estore.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/*
 *  The UserDetailsService interface comes from SPRING SECURITY CORE. It is used to retrieve user-related data.
 * It has one method named loadUserByUsername() which can be overridden to customize the process of finding the user.
 */
public interface UserService extends UserDetailsService {

  UserDTO createUser(UserDTO user);

  UserDTO getUser(String username);
//
//  UserDTO getUserById(String userId);
//
//  UserDTO updateUser(String userId, UserDTO user);
//
//  void deleteUser(String userId);
}
