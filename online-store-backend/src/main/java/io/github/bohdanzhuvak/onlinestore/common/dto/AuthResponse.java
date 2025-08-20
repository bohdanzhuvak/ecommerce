package io.github.bohdanzhuvak.onlinestore.common.dto;

import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import lombok.Data;

@Data
public class AuthResponse {
  private String token;
  private Role role;
  private UserInfo user;
  private TokenInfo tokenInfo;

  @Data
  public static class UserInfo {
    private Long id;
    private String username;
    private String email;
    private Role role;
  }

  @Data
  public static class TokenInfo {
    private long expiresAt;
    private long refreshExpiresAt;
    private String tokenType = "Bearer";
  }
}
