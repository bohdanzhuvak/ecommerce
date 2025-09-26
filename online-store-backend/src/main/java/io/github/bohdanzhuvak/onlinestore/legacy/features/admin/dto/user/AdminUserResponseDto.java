package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.user;

import io.github.bohdanzhuvak.onlinestore.domain.model.Role;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Admin response DTO for User entity with react-admin support.
 * Contains all fields that can be returned to admin client.
 */
@Getter
@Setter
public class AdminUserResponseDto extends AdminResponseDto {

  private String username;
  private String email;
  private Role role;
  private BigDecimal balance;

  // Admin-specific computed fields
  private Boolean isActive = true;
  private String lastLoginAt;
  private Integer totalOrders;
  private BigDecimal totalSpent;

  // Override display name for better admin interface
  @Override
  public String getDisplayName() {
    return username + " (" + email + ")";
  }
}
