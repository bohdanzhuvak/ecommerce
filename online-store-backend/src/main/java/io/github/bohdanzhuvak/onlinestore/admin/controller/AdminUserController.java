package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UserResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@NoArgsConstructor
public class AdminUserController {

  @GetMapping
  public Page<UserResponse> getUsers(Pageable pageable) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    throw new UnsupportedOperationException();
  }
}
