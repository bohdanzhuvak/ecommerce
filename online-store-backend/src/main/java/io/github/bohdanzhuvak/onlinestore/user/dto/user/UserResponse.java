package io.github.bohdanzhuvak.onlinestore.user.dto.user;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private String role;
  private BigDecimal balance;
}
