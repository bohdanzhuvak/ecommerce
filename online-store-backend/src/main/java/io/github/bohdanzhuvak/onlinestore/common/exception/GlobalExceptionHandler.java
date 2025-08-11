package io.github.bohdanzhuvak.onlinestore.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(
        ex.getStatus().value(),
        ex.getStatus().getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(ex.getStatus()).body(response);
  }

  @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleAuthExceptions(Exception ex, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.UNAUTHORIZED.value(),
        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
        "Invalid email or password",
        request.getRequestURI(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(
        HttpStatus.FORBIDDEN.value(),
        HttpStatus.FORBIDDEN.getReasonPhrase(),
        "You do not have permission to access this resource",
        request.getRequestURI(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse(
        500,
        "Internal Server Error",
        "Something went wrong. Please try again later.",
        request.getRequestURI(),
        LocalDateTime.now()
    );

    ex.printStackTrace();
    return ResponseEntity.status(500).body(response);
  }
}
