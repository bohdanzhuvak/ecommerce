package io.github.bohdanzhuvak.onlinestore.infrastructure.exception;

import io.github.bohdanzhuvak.onlinestore.architecture.BusinessRuleViolationException;
import io.github.bohdanzhuvak.onlinestore.architecture.DomainException;
import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.balance.domain.exception.InsufficientFundsException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for the entire application.
 * <p>
 * This class provides centralized exception handling across all controllers,
 * ensuring consistent error responses and proper HTTP status codes.
 * It follows the hexagonal architecture by handling both domain exceptions
 * and infrastructure/framework exceptions.
 * </p>
 * <p>
 * Exception handling strategy:
 * <ul>
 *   <li>Domain exceptions (DomainException and subclasses) - Business logic errors</li>
 *   <li>Validation exceptions - Input validation errors</li>
 *   <li>Security exceptions - Authentication and authorization errors</li>
 *   <li>Technical exceptions - Infrastructure and unexpected errors</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private static final String VALIDATION_FAILED_CODE = "VALIDATION_FAILED";
  private static final String AUTHENTICATION_FAILED_CODE = "AUTHENTICATION_FAILED";
  private static final String ACCESS_DENIED_CODE = "ACCESS_DENIED";
  private static final String INTERNAL_ERROR_CODE = "INTERNAL_SERVER_ERROR";
  private static final String BAD_REQUEST_CODE = "BAD_REQUEST";

  /**
   * Handles ResourceNotFoundException (404 Not Found).
   * Triggered when a requested resource cannot be found.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex,
      HttpServletRequest request
  ) {
    logger.warn("Resource not found: {}", ex.getMessage());

    Map<String, Object> details = new HashMap<>();
    details.put("resourceType", ex.getResourceType());
    details.put("resourceId", ex.getResourceId());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .errorCode(ex.getErrorCode())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .details(details)
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles InsufficientFundsException (422 Unprocessable Entity).
   * Triggered when a user attempts to debit more than their available balance.
   */
  @ExceptionHandler(InsufficientFundsException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientFundsException(
      InsufficientFundsException ex,
      HttpServletRequest request
  ) {
    logger.warn("Insufficient funds: {}", ex.getMessage());

    Map<String, Object> details = new HashMap<>();
    details.put("userId", ex.getUserId().getValue());
    details.put("requestedAmount", ex.getRequestedAmount().getAmount());
    details.put("availableBalance", ex.getAvailableBalance().getAmount());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
        .errorCode(ex.getErrorCode())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .details(details)
        .build();

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  /**
   * Handles BusinessRuleViolationException (422 Unprocessable Entity).
   * Triggered when a business rule is violated.
   */
  @ExceptionHandler(BusinessRuleViolationException.class)
  public ResponseEntity<ErrorResponse> handleBusinessRuleViolationException(
      BusinessRuleViolationException ex,
      HttpServletRequest request
  ) {
    logger.warn("Business rule violation: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
        .errorCode(ex.getErrorCode())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  /**
   * Handles all other DomainException types (422 Unprocessable Entity).
   * This is a catch-all for custom domain exceptions.
   */
  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorResponse> handleDomainException(
      DomainException ex,
      HttpServletRequest request
  ) {
    logger.warn("Domain exception: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
        .errorCode(ex.getErrorCode())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  /**
   * Handles validation errors from @Valid annotations (400 Bad Request).
   * Provides detailed field-level error information.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex,
      HttpServletRequest request
  ) {
    logger.warn("Validation failed: {} field errors", ex.getBindingResult().getFieldErrorCount());

    List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::mapFieldError)
        .collect(Collectors.toList());

    ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .errorCode(VALIDATION_FAILED_CODE)
        .message("Validation failed for request")
        .path(request.getRequestURI())
        .fieldErrors(fieldErrors)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles IllegalArgumentException (400 Bad Request).
   * Typically thrown for invalid method arguments.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex,
      HttpServletRequest request
  ) {
    logger.warn("Illegal argument: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .errorCode(BAD_REQUEST_CODE)
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles IllegalStateException (409 Conflict).
   * Typically thrown when an operation is attempted in an invalid state.
   */
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> handleIllegalStateException(
      IllegalStateException ex,
      HttpServletRequest request
  ) {
    logger.warn("Illegal state: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.CONFLICT.value())
        .error(HttpStatus.CONFLICT.getReasonPhrase())
        .errorCode("INVALID_STATE")
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  /**
   * Handles type mismatch errors (400 Bad Request).
   * Triggered when a request parameter cannot be converted to the expected type.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatchException(
      MethodArgumentTypeMismatchException ex,
      HttpServletRequest request
  ) {
    logger.warn("Type mismatch for parameter '{}': {}", ex.getName(), ex.getMessage());

    String message = String.format(
        "Invalid value for parameter '%s': expected type %s",
        ex.getName(),
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"
    );

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .errorCode(BAD_REQUEST_CODE)
        .message(message)
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles authentication exceptions (401 Unauthorized).
   * Triggered when authentication fails.
   */
  @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
  public ResponseEntity<ErrorResponse> handleAuthenticationException(
      Exception ex,
      HttpServletRequest request
  ) {
    logger.warn("Authentication failed: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.UNAUTHORIZED.value())
        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
        .errorCode(AUTHENTICATION_FAILED_CODE)
        .message("Authentication failed")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  /**
   * Handles access denied exceptions (403 Forbidden).
   * Triggered when a user attempts to access a resource they don't have permission for.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException ex,
      HttpServletRequest request
  ) {
    logger.warn("Access denied: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.FORBIDDEN.value())
        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
        .errorCode(ACCESS_DENIED_CODE)
        .message("Access denied")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  /**
   * Handles NoResourceFoundException (404 Not Found).
   * Triggered when Spring cannot find a handler for the requested endpoint.
   * This typically happens when an endpoint doesn't exist or the path is incorrect.
   */
  @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
      org.springframework.web.servlet.resource.NoResourceFoundException ex,
      HttpServletRequest request
  ) {
    logger.warn("No resource found: {} {}", request.getMethod(), request.getRequestURI());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .errorCode("ENDPOINT_NOT_FOUND")
        .message(String.format("Endpoint '%s %s' not found", request.getMethod(), request.getRequestURI()))
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles NoHandlerFoundException (404 Not Found).
   * Alternative exception that Spring may throw when no handler is found.
   */
  @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
      org.springframework.web.servlet.NoHandlerFoundException ex,
      HttpServletRequest request
  ) {
    logger.warn("No handler found: {} {}", ex.getHttpMethod(), ex.getRequestURL());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .errorCode("ENDPOINT_NOT_FOUND")
        .message(String.format("Endpoint '%s %s' not found", ex.getHttpMethod(), ex.getRequestURL()))
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles HttpRequestMethodNotSupportedException (405 Method Not Allowed).
   * Triggered when an endpoint exists but doesn't support the requested HTTP method.
   */
  @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(
      org.springframework.web.HttpRequestMethodNotSupportedException ex,
      HttpServletRequest request
  ) {
    logger.warn("Method not supported: {} for {}", ex.getMethod(), request.getRequestURI());

    String supportedMethods = ex.getSupportedHttpMethods() != null
        ? String.join(", ", ex.getSupportedHttpMethods().stream().map(Object::toString).toList())
        : "none";

    Map<String, Object> details = new HashMap<>();
    details.put("requestedMethod", ex.getMethod());
    details.put("supportedMethods", supportedMethods);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
        .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
        .errorCode("METHOD_NOT_ALLOWED")
        .message(String.format("Method %s is not supported for this endpoint. Supported methods: %s",
            ex.getMethod(), supportedMethods))
        .path(request.getRequestURI())
        .details(details)
        .build();

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
  }

  /**
   * Handles HttpMediaTypeNotSupportedException (415 Unsupported Media Type).
   * Triggered when the request Content-Type is not supported.
   */
  @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(
      org.springframework.web.HttpMediaTypeNotSupportedException ex,
      HttpServletRequest request
  ) {
    logger.warn("Media type not supported: {}", ex.getContentType());

    String supportedTypes = ex.getSupportedMediaTypes().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
        .error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
        .errorCode("UNSUPPORTED_MEDIA_TYPE")
        .message(String.format("Media type '%s' is not supported. Supported types: %s",
            ex.getContentType(), supportedTypes))
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
  }

  /**
   * Handles all uncaught exceptions (500 Internal Server Error).
   * This is the last line of defense to ensure no exception goes unhandled.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
      Exception ex,
      HttpServletRequest request
  ) {
    logger.error("Unexpected error occurred", ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .errorCode(INTERNAL_ERROR_CODE)
        .message("An unexpected error occurred. Please contact support.")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  private ValidationErrorResponse.FieldError mapFieldError(FieldError fieldError) {
    return new ValidationErrorResponse.FieldError(
        fieldError.getField(),
        fieldError.getRejectedValue(),
        fieldError.getDefaultMessage()
    );
  }
}
