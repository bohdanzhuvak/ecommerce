# Exception Handling Architecture

## Overview

This document describes the exception handling architecture implemented in this application. The solution follows *
*DDD (Domain-Driven Design)** and **Hexagonal Architecture** principles, providing a unified, type-safe approach to
error handling across all layers.

## Architecture Principles

1. **Domain-First**: Exceptions originate from the domain layer and flow outward
2. **Type Safety**: Each business scenario has a specific exception type
3. **Separation of Concerns**: Domain exceptions are independent of infrastructure
4. **Unified HTTP Responses**: All errors return consistent JSON structures
5. **Rich Error Information**: Errors include error codes, details, and context

## Exception Hierarchy

```
DomainException (abstract)
├── ResourceNotFoundException
│   └── BalanceNotFoundException
└── BusinessRuleViolationException
    └── InsufficientFundsException
```

### Base Exception: `DomainException`

Located in `architecture` package, serves as the root for all domain exceptions.

```java
public abstract class DomainException extends RuntimeException {
  public abstract String getErrorCode();
}
```

**Key features:**

- Abstract base for all domain exceptions
- Forces subclasses to define error codes
- Independent of frameworks and infrastructure

### Resource Not Found: `ResourceNotFoundException`

For cases where a requested entity/aggregate cannot be found.

```java
throw new ResourceNotFoundException("User",userId);
throw new

BalanceNotFoundException(userId);
```

**HTTP Status**: 404 Not Found

### Business Rule Violation: `BusinessRuleViolationException`

For violations of business rules and domain invariants.

```java
throw new BusinessRuleViolationException(
  "INSUFFICIENT_FUNDS",
  "Cannot debit amount greater than available balance"
);
```

**HTTP Status**: 422 Unprocessable Entity

## Module-Specific Exceptions

### Balance Module

#### `BalanceNotFoundException`

```java
package io.github.bohdanzhuvak.onlinestore.balance.domain.exception;

// Usage in use cases
Balance balance = balanceRepository
    .findByUserId(userId)
    .orElseThrow(() -> new BalanceNotFoundException(userId));
```

#### `InsufficientFundsException`

```java
package io.github.bohdanzhuvak.onlinestore.balance.domain.exception;

// Usage in domain logic or use cases
if (balance.getAmount().isLessThan(requestedAmount)) {
  throw new InsufficientFundsException(userId, requestedAmount, balance.getAmount());
}
```

## Global Exception Handler

Located in `infrastructure.exception.GlobalExceptionHandler`, this component:

- Catches all exceptions across all controllers
- Maps exceptions to appropriate HTTP status codes
- Builds standardized error responses
- Logs errors with appropriate severity levels

### Supported Exception Types

| Exception Type                           | HTTP Status | Error Code             | Description                |
|------------------------------------------|-------------|------------------------|----------------------------|
| **Domain Exceptions**                    |             |                        |                            |
| `ResourceNotFoundException`              | 404         | RESOURCE_NOT_FOUND     | Resource not found         |
| `InsufficientFundsException`             | 422         | INSUFFICIENT_FUNDS     | Insufficient funds         |
| `BusinessRuleViolationException`         | 422         | Custom                 | Business rule violated     |
| `DomainException`                        | 422         | Custom                 | General domain error       |
| **Validation Exceptions**                |             |                        |                            |
| `MethodArgumentNotValidException`        | 400         | VALIDATION_FAILED      | Request validation failed  |
| `IllegalArgumentException`               | 400         | BAD_REQUEST            | Invalid argument           |
| `MethodArgumentTypeMismatchException`    | 400         | BAD_REQUEST            | Type mismatch              |
| `IllegalStateException`                  | 409         | INVALID_STATE          | Invalid state              |
| **HTTP/Spring Exceptions**               |             |                        |                            |
| `NoResourceFoundException`               | 404         | ENDPOINT_NOT_FOUND     | Endpoint does not exist    |
| `NoHandlerFoundException`                | 404         | ENDPOINT_NOT_FOUND     | No handler for endpoint    |
| `HttpRequestMethodNotSupportedException` | 405         | METHOD_NOT_ALLOWED     | HTTP method not supported  |
| `HttpMediaTypeNotSupportedException`     | 415         | UNSUPPORTED_MEDIA_TYPE | Content-Type not supported |
| **Security Exceptions**                  |             |                        |                            |
| `AuthenticationException`                | 401         | AUTHENTICATION_FAILED  | Authentication failed      |
| `AccessDeniedException`                  | 403         | ACCESS_DENIED          | Access denied              |
| **Catch-All**                            |             |                        |                            |
| `Exception`                              | 500         | INTERNAL_SERVER_ERROR  | Unexpected error           |

## Error Response Format

### Standard Error Response

```json
{
  "timestamp": "2025-10-25T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "errorCode": "RESOURCE_NOT_FOUND",
  "message": "Balance with id 'user-123' not found",
  "path": "/api/balance/user-123",
  "details": {
    "resourceType": "Balance",
    "resourceId": "user-123"
  }
}
```

### Validation Error Response

For `@Valid` annotation failures:

```json
{
  "timestamp": "2025-10-25T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "errorCode": "VALIDATION_FAILED",
  "message": "Validation failed for request",
  "path": "/api/users",
  "fieldErrors": [
    {
      "field": "email",
      "rejectedValue": "invalid-email",
      "message": "must be a valid email address"
    },
    {
      "field": "password",
      "rejectedValue": null,
      "message": "must not be blank"
    }
  ]
}
```

### Insufficient Funds Error Response

```json
{
  "timestamp": "2025-10-25T10:30:00Z",
  "status": 422,
  "error": "Unprocessable Entity",
  "errorCode": "INSUFFICIENT_FUNDS",
  "message": "Insufficient funds for user user-123. Requested: 100.00, Available: 50.00",
  "path": "/api/balance/debit",
  "details": {
    "userId": "user-123",
    "requestedAmount": 100.00,
    "availableBalance": 50.00
  }
}
```

### Endpoint Not Found Error Response

For non-existent endpoints (404 instead of 500):

```json
{
  "timestamp": "2025-10-25T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "errorCode": "ENDPOINT_NOT_FOUND",
  "message": "Endpoint 'GET /api/v1/admin/categories' not found",
  "path": "/api/v1/admin/categories"
}
```

### Method Not Allowed Error Response

When endpoint exists but HTTP method is not supported:

```json
{
  "timestamp": "2025-10-25T10:30:00Z",
  "status": 405,
  "error": "Method Not Allowed",
  "errorCode": "METHOD_NOT_ALLOWED",
  "message": "Method POST is not supported for this endpoint. Supported methods: GET, PUT",
  "path": "/api/users/123",
  "details": {
    "requestedMethod": "POST",
    "supportedMethods": "GET, PUT"
  }
}
```

## Creating New Exceptions

### 1. Domain-Level Exception (Shared)

For cross-cutting exceptions used by multiple modules:

```java
// Location: architecture/YourException.java
package io.github.bohdanzhuvak.onlinestore.architecture;

public class YourException extends DomainException {
  private static final String ERROR_CODE = "YOUR_ERROR_CODE";

  public YourException(String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return ERROR_CODE;
  }
}
```

### 2. Module-Specific Exception

For exceptions specific to a business module:

```java
// Location: <module>/domain/exception/YourException.java
package io.github.bohdanzhuvak.onlinestore.yourmodule.domain.exception;

import io.github.bohdanzhuvak.onlinestore.architecture.BusinessRuleViolationException;

public class YourSpecificException extends BusinessRuleViolationException {
  private static final String ERROR_CODE = "YOUR_ERROR_CODE";

  public YourSpecificException(String message) {
    super(ERROR_CODE, message);
  }

  // Add domain-specific fields and getters if needed
  private final YourDomainObject domainObject;

  public YourSpecificException(YourDomainObject domainObject, String message) {
    super(ERROR_CODE, message);
    this.domainObject = domainObject;
  }

  public YourDomainObject getDomainObject() {
    return domainObject;
  }
}
```

### 3. Add Handler (if custom response needed)

If the exception requires special handling beyond the default:

```java
// Add to GlobalExceptionHandler.java
@ExceptionHandler(YourSpecificException.class)
public ResponseEntity<ErrorResponse> handleYourSpecificException(
    YourSpecificException ex,
    HttpServletRequest request
) {
  logger.warn("Your specific error: {}", ex.getMessage());

  Map<String, Object> details = new HashMap<>();
  details.put("customField", ex.getDomainObject().getSomeValue());

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
```

## Best Practices

### 1. Use Specific Exceptions

❌ **Bad:**

```java
throw new RuntimeException("Balance not found");
throw new IllegalArgumentException("Insufficient funds");
```

✅ **Good:**

```java
throw new BalanceNotFoundException(userId);
throw new

InsufficientFundsException(userId, requestedAmount, availableBalance);
```

### 2. Throw from Domain Layer

Exceptions should originate from domain entities or use cases, not controllers.

❌ **Bad:**

```java
// In Controller
@PostMapping("/debit")
public void debitBalance(@RequestBody DebitRequest request) {
  if (balance < request.amount()) {
    throw new InsufficientFundsException(...)
  }
}
```

✅ **Good:**

```java
// In Domain Entity
public Balance subtract(Money amount) {
  if (this.amount.isLessThan(amount)) {
    throw new InsufficientFundsException(...)
  }
  return new Balance(this.userId, this.amount.subtract(amount));
}
```

### 3. Include Rich Context

Provide as much context as possible for debugging and user feedback.

❌ **Bad:**

```java
throw new BalanceNotFoundException("Not found");
```

✅ **Good:**

```java
throw new BalanceNotFoundException(userId);
// Exception message: "Balance with id 'user-123' not found"
```

### 4. Never Catch Domain Exceptions in Controllers

Let the Global Exception Handler deal with them.

❌ **Bad:**

```java

@GetMapping("/balance/{userId}")
public ResponseEntity<?> getBalance(@PathVariable String userId) {
  try {
    Balance balance = balanceService.getBalance(userId);
    return ResponseEntity.ok(balance);
  } catch (BalanceNotFoundException e) {
    return ResponseEntity.notFound().build();
  }
}
```

✅ **Good:**

```java

@GetMapping("/balance/{userId}")
public ResponseEntity<BalanceResponse> getBalance(@PathVariable String userId) {
  Balance balance = balanceService.getBalance(userId);
  return ResponseEntity.ok(mapToResponse(balance));
}
// Let GlobalExceptionHandler handle BalanceNotFoundException
```

### 5. Use Error Codes for Client-Side Handling

Error codes enable programmatic error handling and i18n on the client side.

```typescript
// Frontend example
if (error.errorCode === 'INSUFFICIENT_FUNDS') {
  showMessage(t('errors.insufficientFunds', error.details));
} else if (error.errorCode === 'RESOURCE_NOT_FOUND') {
  redirectToNotFound();
}
```

## Migration Guide

To migrate existing `RuntimeException` throws:

1. **Identify the business scenario**: What business rule failed?
2. **Choose or create an exception**: Use existing or create new domain exception
3. **Replace the throw statement**: Use the specific exception
4. **Update tests**: Verify the correct exception is thrown

### Example Migration

**Before:**

```java
Balance balance = balanceRepository
    .findByUserId(userId)
    .orElseThrow(() -> new RuntimeException("Balance not found for user: " + userId));
```

**After:**

```java
Balance balance = balanceRepository
    .findByUserId(userId)
    .orElseThrow(() -> new BalanceNotFoundException(userId));
```

## Testing Exception Handling

### Unit Test Example

```java

@Test
void shouldThrowBalanceNotFoundExceptionWhenBalanceDoesNotExist() {
  // Given
  UserId userId = UserId.of("user-123");
  when(balanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

  // When & Then
  assertThatThrownBy(() -> useCase.execute(new Command(userId, Money.of(100, "USD"))))
      .isInstanceOf(BalanceNotFoundException.class)
      .hasMessageContaining("Balance with id 'user-123' not found");
}
```

### Integration Test Example

```java
@Test
void shouldReturn404WhenBalanceNotFound() throws Exception {
  mockMvc.perform(get("/api/balance/unknown-user"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"))
      .andExpect(jsonPath("$.message").value(containsString("not found")));
}
```

## Summary

This exception handling architecture provides:

- ✅ **Type-safe** error handling
- ✅ **Consistent** HTTP responses
- ✅ **DDD-aligned** exception hierarchy
- ✅ **Hexagonal Architecture** compliant
- ✅ **Rich error context** for debugging and user feedback
- ✅ **Extensible** design for new exception types
- ✅ **Client-friendly** error codes and details

All exceptions now flow from domain → application → infrastructure, maintaining clean architecture boundaries while
providing excellent error handling capabilities.
