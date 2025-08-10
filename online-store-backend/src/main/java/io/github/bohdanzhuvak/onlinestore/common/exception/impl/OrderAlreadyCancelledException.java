package io.github.bohdanzhuvak.onlinestore.common.exception.impl;

import io.github.bohdanzhuvak.onlinestore.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OrderAlreadyCancelledException extends BaseException {
  public OrderAlreadyCancelledException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
