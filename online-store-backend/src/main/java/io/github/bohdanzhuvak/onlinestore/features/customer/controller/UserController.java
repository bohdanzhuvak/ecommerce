package io.github.bohdanzhuvak.onlinestore.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.auth.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.UserService;
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
    return userService.getUser(user.getId());
  }

  @PutMapping
  public UserResponse updateCurrentUser(@CurrentUser UserPrincipal user, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    return userService.updateUser(user.getId(), updateUserRequest);
  }

  @DeleteMapping
  public void deleteCurrentUser(@CurrentUser UserPrincipal user) {
    userService.deleteUser(user.getId());
  }
}