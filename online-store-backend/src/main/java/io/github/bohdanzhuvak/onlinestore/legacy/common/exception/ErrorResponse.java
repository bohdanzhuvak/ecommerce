package io.github.bohdanzhuvak.onlinestore.legacy.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    LocalDateTime timestamp,
    Map<String, String> details
) {

  public ErrorResponse(int status, String error, String message, String path, LocalDateTime timestamp) {
    this(status, error, message, path, timestamp, null);
  }
}
