package com.den.estore.controllers;

import com.den.estore.dto.UserDTO;
import com.den.estore.exceptions.UserServiceException;
import com.den.estore.model.request.UserSignUpRequestModel;
import com.den.estore.model.response.ErrorMessages;
import com.den.estore.model.response.UserRes;
import com.den.estore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UsersController {

  @Autowired
  UserService userService;

  @PostMapping("/signup")
  public UserRes createUser(@RequestBody UserSignUpRequestModel userDetails) {

    if(userDetails.getUsername().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

    ModelMapper modelMapper = new ModelMapper();
    UserDTO userDto = modelMapper.map(userDetails, UserDTO.class);

    UserDTO createdUser = userService.createUser(userDto);

    UserRes returnValue = new UserRes();

    returnValue = modelMapper.map(createdUser, UserRes.class);

    return returnValue;

  }

}
