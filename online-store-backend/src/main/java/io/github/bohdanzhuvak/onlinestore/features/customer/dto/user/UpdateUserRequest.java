package io.github.bohdanzhuvak.onlinestore.features.customer.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
  private String username;
  private String email;
}
