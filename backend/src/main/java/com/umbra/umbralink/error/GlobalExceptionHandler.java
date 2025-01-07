package com.umbra.umbralink.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MessageNotFoundException.class)
  public ResponseEntity<ErrorObject> handleMessageNotFoundException(MessageNotFoundException ex) {
    ErrorObject error = new ErrorObject();
    error.setErrorCode(HttpStatus.NOT_FOUND.value());
    error.setMessage(ex.getMessage());
    error.setTimestamp(new Date(System.currentTimeMillis()));

    return new ResponseEntity<ErrorObject>(error, HttpStatus.NOT_FOUND);
  }

}
