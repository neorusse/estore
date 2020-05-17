package com.den.estore.service;

import com.den.estore.dto.UserDTO;
import com.den.estore.exceptions.UserServiceException;
import com.den.estore.io.entity.Cart;
import com.den.estore.io.entity.Users;
import com.den.estore.io.repository.CartRepository;
import com.den.estore.io.repository.UserRepository;
import com.den.estore.service.impl.UserServiceImpl;
import com.den.estore.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {

  // inject a mock of the class under test
  @InjectMocks
  UserServiceImpl userService;

  // mock external class
  @Mock
  UserRepository userRepository;

  @Mock
  CartRepository cartRepository;

  @Mock
  Utils utils;

  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;

  String userId = "hhty57ehfy";
  String encryptedPassword = "74hghd8474jf";

  Users userEntity;

  @BeforeEach
  void setUp() throws Exception {

    // Enables Mockito to instantiate our injected UserServiceImpl class
    MockitoAnnotations.initMocks(this);

    userEntity = new Users();
    userEntity.setId(1L);
    userEntity.setFirstName("Russell");
    userEntity.setLastName("Doe");
    userEntity.setUserId(userId);
    userEntity.setUsername("ruscom");
    userEntity.setEncryptedPassword(encryptedPassword);
    userEntity.setEmail("test@test.com");

    Cart cart = new Cart();
    cartRepository.save(cart);


    userEntity.setCart(cart);
  }

  @Test
  final void testCreateUser() {

    when(userRepository.findByEmail(anyString())).thenReturn(null);
    when(utils.generateUserId(anyInt())).thenReturn(userId);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
    when(userRepository.save(any(Users.class))).thenReturn(userEntity);

    UserDTO userDto = new UserDTO();


    userDto.setFirstName("Russell");
    userDto.setLastName("Doe");
    userDto.setEmail("test@test.com");
    userDto.setUsername("ruscom");
    userDto.setPassword("12345678");
    userDto.setConfirmPassword("12345678");

    UserDTO storedUserDetails = userService.createUser(userDto);

    assertNotNull(storedUserDetails);
    assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
    assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
    assertNotNull(storedUserDetails.getUserId());

    verify(bCryptPasswordEncoder, times(1)).encode("12345678");
    verify(userRepository,times(1)).save(any(Users.class));
  }

  @Test
  final void testCreateUser_CreateUserServiceException()
  {
    when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

    UserDTO userDto = new UserDTO();
    userDto.setFirstName("Russell");
    userDto.setLastName("Nyorere");
    userDto.setPassword("12345678");
    userDto.setEmail("test@test.com");

    assertThrows(UserServiceException.class,

      () -> {
        userService.createUser(userDto);
      }

    );
  }

}
