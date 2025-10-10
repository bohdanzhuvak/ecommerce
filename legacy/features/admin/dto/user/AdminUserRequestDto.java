package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.user;

import io.github.bohdanzhuvak.onlinestore.domain.model.Role;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminRequestDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Admin request DTO for User entity.
 * Contains only fields that can be provided by admin client.
 */
@Getter
@Setter
public class AdminUserRequestDto extends AdminRequestDto {

  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscores")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = 100, message = "Email must not exceed 100 characters")
  private String email;

  @NotNull(message = "Role is required")
  private Role role;

  @NotNull(message = "Balance is required")
  @DecimalMin(value = "0.0", message = "Balance must be non-negative")
  private BigDecimal balance;
}
