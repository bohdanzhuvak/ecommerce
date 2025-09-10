package io.github.bohdanzhuvak.onlinestore.common.exception.impl;

import io.github.bohdanzhuvak.onlinestore.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
  
  public NotFoundException(String message, HttpStatus status) {
    super(message, status);
  }
}
