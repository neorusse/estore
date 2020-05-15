package com.den.estore.service.impl;

import com.den.estore.dto.UserDTO;
import com.den.estore.exceptions.UserServiceException;
import com.den.estore.io.entity.UserEntity;
import com.den.estore.io.repository.UserRepository;
import com.den.estore.model.response.ErrorMessages;
import com.den.estore.service.UserService;
import com.den.estore.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Override
  public UserDTO createUser(UserDTO user) {

    if (userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

    if (!user.getPassword().equals(user.getConfirmPassword())) throw new RuntimeException("Passwords do not match");

    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(user, UserEntity.class);

    String publicUserId = utils.generateUserId(30);

    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword("eudndilss");

    UserEntity storedUserDetails = userRepository.save(userEntity);

    UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);

    return returnValue;
  }

}
