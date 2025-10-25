package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.PagedResponse;
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

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {
  private final WebUserOrchestratorService webUserOrchestratorService;

  public UserAdminController(WebUserOrchestratorService webUserOrchestratorService) {
    this.webUserOrchestratorService = webUserOrchestratorService;
  }

  @GetMapping
  public ResponseEntity<PagedResponse<UserResponse>> getAllUsers(
      @RequestParam(required = false) String role,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int perPage) {
    try {
      UserRole userRole = role != null ? UserRole.fromString(role) : null;
      PageResult<User> pageResult = webUserOrchestratorService.getAllUsers(userRole, activeOnly, page, perPage);
      PagedResponse<UserResponse> response = PagedResponse.from(pageResult, UserResponse::from);
      return ResponseEntity.ok(response);
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
