package com.goods.product.task7.starter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerStarter {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
  }
}
