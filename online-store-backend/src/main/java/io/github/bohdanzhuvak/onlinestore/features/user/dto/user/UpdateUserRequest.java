package io.github.bohdanzhuvak.onlinestore.features.user.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
  private String username;
  private String email;
}
