package io.github.bohdanzhuvak.onlinestore.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Specialized error response for validation errors.
 * <p>
 * This extends the standard error response to include field-level validation errors,
 * making it easy for clients to display validation feedback to users.
 * </p>
 * <p>
 * Example JSON response:
 * <pre>
 * {
 *   "timestamp": "2025-10-25T10:30:00Z",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "errorCode": "VALIDATION_FAILED",
 *   "message": "Validation failed for request",
 *   "path": "/api/users",
 *   "fieldErrors": [
 *     {
 *       "field": "email",
 *       "rejectedValue": "invalid-email",
 *       "message": "must be a valid email address"
 *     },
 *     {
 *       "field": "password",
 *       "rejectedValue": null,
 *       "message": "must not be blank"
 *     }
 *   ]
 * }
 * </pre>
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String errorCode,
    String message,
    String path,
    List<FieldError> fieldErrors
) {

  public static Builder builder() {
    return new Builder();
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public record FieldError(
      String field,
      Object rejectedValue,
      String message
  ) {
  }

  public static class Builder {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String errorCode;
    private String message;
    private String path;
    private List<FieldError> fieldErrors;

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

    public Builder fieldErrors(List<FieldError> fieldErrors) {
      this.fieldErrors = fieldErrors;
      return this;
    }

    public ValidationErrorResponse build() {
      return new ValidationErrorResponse(timestamp, status, error, errorCode, message, path, fieldErrors);
    }
  }
}
