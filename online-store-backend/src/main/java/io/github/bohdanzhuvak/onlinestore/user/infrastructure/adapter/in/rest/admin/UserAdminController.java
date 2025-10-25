package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.user.application.service.WebUserOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {
  private final WebUserOrchestratorService webUserOrchestratorService;

  public UserAdminController(WebUserOrchestratorService webUserOrchestratorService) {
    this.webUserOrchestratorService = webUserOrchestratorService;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers(
      @RequestParam(required = false) String role,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserRole userRole = role != null ? UserRole.fromString(role) : null;
      List<User> users = webUserOrchestratorService.getAllUsers(userRole, activeOnly, offset, limit);
      return ResponseEntity.ok(UserResponse.from(users));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{userId}/activate")
  public ResponseEntity<UserResponse> activateUser(@PathVariable String userId) {
    try {
      User user = webUserOrchestratorService.activateUser(UserId.of(userId));
      return ResponseEntity.ok(UserResponse.from(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{userId}/deactivate")
  public ResponseEntity<UserResponse> deactivateUser(@PathVariable String userId) {
    try {
      User user = webUserOrchestratorService.deactivateUser(UserId.of(userId));
      return ResponseEntity.ok(UserResponse.from(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
