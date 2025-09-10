package io.github.bohdanzhuvak.onlinestore.common.constants;

public final class ApiConstants {

  // API Version
  public static final String API_VERSION = "/api/v1";
  // Common endpoints
  public static final String ID_PATH = "/{id}";
  public static final String ALL_PATH = "/all";
  public static final String COUNT_PATH = "/count";
  // Pagination
  public static final String DEFAULT_PAGE_SIZE = "20";
  public static final String MAX_PAGE_SIZE = "100";
  // Common messages
  public static final String NOT_FOUND_MESSAGE = "Resource not found";
  public static final String VALIDATION_ERROR_MESSAGE = "Validation error";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
  // Security
  public static final String BEARER_PREFIX = "Bearer ";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  // Date formats
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  private ApiConstants() {
    // Utility class
  }
}
