package io.github.bohdanzhuvak.onlinestore.features.admin.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
  private String username;
  private String email;
  private String role;
}
