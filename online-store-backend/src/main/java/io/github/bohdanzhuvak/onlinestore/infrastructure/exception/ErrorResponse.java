package io.github.bohdanzhuvak.onlinestore.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

/**
 * Standardized error response structure for all API errors.
 * <p>
 * This DTO provides a consistent format for error responses across the entire application,
 * making it easier for clients to handle errors programmatically.
 * </p>
 * <p>
 * Example JSON response:
 * <pre>
 * {
 *   "timestamp": "2025-10-25T10:30:00Z",
 *   "status": 404,
 *   "error": "Not Found",
 *   "errorCode": "RESOURCE_NOT_FOUND",
 *   "message": "Balance with id 'user-123' not found",
 *   "path": "/api/balance/user-123",
 *   "details": {
 *     "resourceType": "Balance",
 *     "resourceId": "user-123"
 *   }
 * }
 * </pre>
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String errorCode,
    String message,
    String path,
    Map<String, Object> details
) {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String errorCode;
    private String message;
    private String path;
    private Map<String, Object> details;

    public Builder timestamp(Instant timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder status(int status) {
      this.status = status;
      return this;
    }

    public Builder error(String error) {
      this.error = error;
      return this;
    }

    public Builder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder details(Map<String, Object> details) {
      this.details = details;
      return this;
    }

    public ErrorResponse build() {
      return new ErrorResponse(timestamp, status, error, errorCode, message, path, details);
    }
  }
}
