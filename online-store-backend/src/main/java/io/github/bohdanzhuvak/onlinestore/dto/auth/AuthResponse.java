package io.github.bohdanzhuvak.onlinestore.dto.auth;

import io.github.bohdanzhuvak.onlinestore.dto.user.UserResponse;
import lombok.Data;

@Data
public class AuthResponse {
  private String token;
  private UserResponse user;
}
