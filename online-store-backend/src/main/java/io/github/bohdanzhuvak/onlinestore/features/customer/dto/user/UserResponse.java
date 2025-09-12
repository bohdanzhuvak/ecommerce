package io.github.bohdanzhuvak.onlinestore.features.customer.dto.user;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.CustomerResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends CustomerResponseDto {
  private String username;
  private String email;
  private String role;
  private BigDecimal balance;
}
