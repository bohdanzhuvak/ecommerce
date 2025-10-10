package io.github.bohdanzhuvak.onlinestore.user.application.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
