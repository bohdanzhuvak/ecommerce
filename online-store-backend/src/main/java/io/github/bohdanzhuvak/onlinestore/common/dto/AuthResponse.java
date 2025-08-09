package io.github.bohdanzhuvak.onlinestore.common.dto;

import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import lombok.Data;

@Data
public class AuthResponse {
  private String token;
  private Role role;
}
