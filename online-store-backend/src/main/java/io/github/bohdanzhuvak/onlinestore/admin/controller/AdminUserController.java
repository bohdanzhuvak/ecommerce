package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.admin.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
  private final AdminUserService adminUserService;

  @GetMapping
  public Page<UserResponse> getUsers(Pageable pageable) {
    return adminUserService.getUsers(pageable);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return adminUserService.getUser(id);
  }

  @PutMapping("/{id}")
  public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    return adminUserService.updateUser(id, updateUserRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    adminUserService.deleteUser(id);
  }
}
