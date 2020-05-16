package com.den.estore.security;

import com.den.estore.SpringApplicationContext;
import com.den.estore.dto.UserDTO;
import com.den.estore.model.request.UserLoginRequestModel;
import com.den.estore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  // Authenticates user login
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

    UserLoginRequestModel userCredentials = null;

    try {

      // Grab user credentials and map them to UserLoginRequestModel
      userCredentials = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequestModel.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Create login token
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
      userCredentials.getUsername(),
      userCredentials.getPassword(),
      new ArrayList<>()
    );

    // authenticate user
    Authentication auth = authenticationManager.authenticate(authenticationToken);

    return auth;
  }

  // When user is authenticated, call this method
  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    String userName = ((User) authResult.getPrincipal()).getUsername();

    // Create JWT Token
    String token = Jwts.builder()
      .setSubject(userName)
      .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
      .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
      .compact();

    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

    UserDTO userDto = userService.getUser(userName);

    // Add Token to response
    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    res.addHeader("UserID", userDto.getUserId());

  }

}
