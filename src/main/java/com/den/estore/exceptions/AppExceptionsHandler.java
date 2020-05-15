package com.den.estore.exceptions;

import com.den.estore.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {

  // handles UserServiceException
  @ExceptionHandler(value = {UserServiceException.class})
  public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {

    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // handles all other exceptions
  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {

    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}