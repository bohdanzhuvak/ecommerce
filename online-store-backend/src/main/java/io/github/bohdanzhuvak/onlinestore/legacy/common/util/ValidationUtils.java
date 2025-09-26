package io.github.bohdanzhuvak.onlinestore.legacy.common.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public final class ValidationUtils {

  // Email validation pattern
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
  );
  // Username validation pattern (alphanumeric and underscore, 3-20 characters)
  private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");

  private ValidationUtils() {
    // Utility class
  }

  /**
   * Проверить валидность email
   */
  public static boolean isValidEmail(String email) {
    return StringUtils.hasText(email) && EMAIL_PATTERN.matcher(email).matches();
  }

  /**
   * Проверить валидность username
   */
  public static boolean isValidUsername(String username) {
    return StringUtils.hasText(username) && USERNAME_PATTERN.matcher(username).matches();
  }

  /**
   * Проверить что строка не пустая
   */
  public static boolean isNotBlank(String str) {
    return StringUtils.hasText(str);
  }

  /**
   * Проверить что строка пустая
   */
  public static boolean isBlank(String str) {
    return !StringUtils.hasText(str);
  }

  /**
   * Проверить что число положительное
   */
  public static boolean isPositive(Number number) {
    return number != null && number.doubleValue() > 0;
  }

  /**
   * Проверить что число не отрицательное
   */
  public static boolean isNonNegative(Number number) {
    return number != null && number.doubleValue() >= 0;
  }
}
