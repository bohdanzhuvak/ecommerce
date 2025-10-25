package io.github.bohdanzhuvak.onlinestore.architecture;

/**
 * Exception thrown when a requested resource cannot be found.
 * <p>
 * This is a common exception type used across all modules when
 * an entity or aggregate cannot be located by its identifier.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *   User user = userRepository.findById(userId)
 *       .orElseThrow(() -> new ResourceNotFoundException("User", userId.value()));
 * </pre>
 * </p>
 */
public class ResourceNotFoundException extends DomainException {

  private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";
  private final String resourceType;
  private final String resourceId;

  public ResourceNotFoundException(String resourceType, String resourceId) {
    super(String.format("%s with id '%s' not found", resourceType, resourceId));
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  public ResourceNotFoundException(String resourceType, Object resourceId) {
    this(resourceType, String.valueOf(resourceId));
  }

  public ResourceNotFoundException(String message) {
    super(message);
    this.resourceType = "Resource";
    this.resourceId = "unknown";
  }

  @Override
  public String getErrorCode() {
    return ERROR_CODE;
  }

  public String getResourceType() {
    return resourceType;
  }

  public String getResourceId() {
    return resourceId;
  }
}
