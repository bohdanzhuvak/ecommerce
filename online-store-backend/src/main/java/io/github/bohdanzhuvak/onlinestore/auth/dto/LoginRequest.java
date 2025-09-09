package io.github.bohdanzhuvak.onlinestore.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
