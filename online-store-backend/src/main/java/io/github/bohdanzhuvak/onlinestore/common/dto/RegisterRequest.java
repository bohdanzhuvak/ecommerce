package io.github.bohdanzhuvak.onlinestore.common.dto;

import lombok.Data;

@Data
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
}
