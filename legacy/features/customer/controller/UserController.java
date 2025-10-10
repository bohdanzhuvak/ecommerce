package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.UserService;
import io.github.bohdanzhuvak.onlinestore.legacy.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/me")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public UserResponse getCurrentUser(@CurrentUser UserPrincipal user) {
    return userService.getUser(user.id());
  }

  @PutMapping
  public UserResponse updateCurrentUser(@CurrentUser UserPrincipal user, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    return userService.updateUser(user.id(), updateUserRequest);
  }

  @DeleteMapping
  public void deleteCurrentUser(@CurrentUser UserPrincipal user) {
    userService.deleteUser(user.id());
  }
}
