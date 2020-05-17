package com.den.estore.controllers;

import com.den.estore.dto.UserDTO;
import com.den.estore.model.request.UserSignUpRequestModel;
import com.den.estore.model.response.UserRes;
import com.den.estore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UsersControllerTest {

  // inject a mock of the class under test
  @InjectMocks
  UsersController usersController;

  // mock external class
  @Mock
  UserServiceImpl userService;

  @Mock
  private ModelMapper modelMapper;

  private UserDTO userDTO = new UserDTO();

  UserSignUpRequestModel userSignUpRequestModel;


  @BeforeEach
  void setUp() throws Exception {

    // Enables Mockito to instantiate our injected UserServiceImpl class
    MockitoAnnotations.initMocks(this);

    when(modelMapper.map(any(), any())).thenReturn(userDTO);

    userDTO.setFirstName("Russell");
    userDTO.setLastName("Doe");
    userDTO.setEmail("test@test.com");
    userDTO.setUsername("ruscom");
    userDTO.setPassword("xcf58tugh47");
    userDTO.setConfirmPassword("xcf58tugh47");

  }

  @Test
  final void testCreateUser() {

    when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

    userSignUpRequestModel = new UserSignUpRequestModel();

    userSignUpRequestModel.setFirstName("Russell");
    userSignUpRequestModel.setLastName("Doe");
    userSignUpRequestModel.setEmail("test@test.com");
    userSignUpRequestModel.setUsername("ruscom");
    userSignUpRequestModel.setPassword("xcf58tugh47");
    userSignUpRequestModel.setConfirmPassword("xcf58tugh47");

    UserRes userRes = usersController.createUser(userSignUpRequestModel);

    assertNotNull(userRes);
    assertEquals(userSignUpRequestModel.getFirstName(), userRes.getFirstName());
    assertEquals(userSignUpRequestModel.getLastName(), userRes.getLastName());
  }

}
