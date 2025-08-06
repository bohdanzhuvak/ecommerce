package io.github.bohdanzhuvak.onlinestore.controller;

import io.github.bohdanzhuvak.onlinestore.dto.user.UserResponse;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@NoArgsConstructor
public class UserController {

  @GetMapping("/me")
  public UserResponse getCurrentUser() {
    throw new UnsupportedOperationException();
  }
}
