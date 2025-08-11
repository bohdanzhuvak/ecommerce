package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.user.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepository;

  @GetMapping("/me")
  public UserResponse getCurrentUser(@CurrentUser UserPrincipal user) {
    var entity = userRepository.findById(user.getId()).orElseThrow();
    return UserResponse.builder()
        .id(entity.getId())
        .username(entity.getUsername())
        .email(entity.getEmail())
        .role(entity.getRole().name())
        .build();
  }
}
