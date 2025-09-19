package io.github.bohdanzhuvak.onlinestore.auth.api.admin;

import io.github.bohdanzhuvak.onlinestore.auth.application.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
  private final AuthApplicationService authApplicationService;

  public UserAdminController(AuthApplicationService authApplicationService) {
    this.authApplicationService = authApplicationService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers(
      @RequestParam(required = false) String role,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserRole userRole = role != null ? UserRole.fromString(role) : null;
      List<User> users = authApplicationService.getAllUsers(userRole, offset, limit);
      return ResponseEntity.ok(users);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{userId}/activate")
  public ResponseEntity<User> activateUser(@PathVariable String userId) {
    try {
      User user = authApplicationService.activateUser(UserId.of(userId));
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{userId}/deactivate")
  public ResponseEntity<User> deactivateUser(@PathVariable String userId) {
    try {
      User user = authApplicationService.deactivateUser(UserId.of(userId));
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
