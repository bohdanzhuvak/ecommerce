package io.github.bohdanzhuvak.onlinestore.user.application.exception;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;

/**
 * Exception thrown when a user cannot be found.
 * <p>
 * This is maintained for backward compatibility but extends the shared
 * ResourceNotFoundException to ensure consistent error handling.
 * </p>
 *
 * @deprecated Use {@link ResourceNotFoundException} directly or create a specific UserNotFoundException
 */
@Deprecated
public class NotFoundException extends ResourceNotFoundException {

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String resourceType, String resourceId) {
    super(resourceType, resourceId);
  }
}
