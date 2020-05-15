package com.den.estore.service.impl;

import com.den.estore.dto.UserDTO;
import com.den.estore.exceptions.UserServiceException;
import com.den.estore.io.entity.UserEntity;
import com.den.estore.io.repository.UserRepository;
import com.den.estore.model.response.ErrorMessages;
import com.den.estore.service.UserService;
import com.den.estore.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDTO createUser(UserDTO user) {

    if (userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Email already exists");

    if (userRepository.findByUsername(user.getUsername()) != null) throw new RuntimeException("Username already exists");

    if (!user.getPassword().equals(user.getConfirmPassword())) throw new RuntimeException("Passwords do not match");

    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(user, UserEntity.class);

    String publicUserId = utils.generateUserId(30);

    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    UserEntity storedUserDetails = userRepository.save(userEntity);

    UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);

    return returnValue;
  }

  // loadUserByUsername() method of the UserDetailsService being over-riden here
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity userEntity = userRepository.findByUsername(username);

    if (userEntity == null) throw new UsernameNotFoundException(username);

    return new User(userEntity.getUsername(), userEntity.getEncryptedPassword(), new ArrayList<>());
  }

  @Override
  public UserDTO getUser(String username) {
    UserEntity userEntity = userRepository.findByUsername(username);

    if (userEntity == null) throw new UsernameNotFoundException(username);

    UserDTO returnValue = new UserDTO();
    BeanUtils.copyProperties(userEntity, returnValue);

    return returnValue;
  }

}
