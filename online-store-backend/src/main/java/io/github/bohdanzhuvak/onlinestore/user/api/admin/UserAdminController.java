package io.github.bohdanzhuvak.onlinestore.user.api.admin;

import io.github.bohdanzhuvak.onlinestore.user.application.UserApplicationService;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
  private final UserApplicationService userApplicationService;

  public UserAdminController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
    try {
      User user = userApplicationService.createUser(
          Email.of(request.getEmail()),
          request.getPassword(),
          request.getFirstName(),
          request.getLastName(),
          UserRole.fromString(request.getRole())
      );
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers(
      @RequestParam(required = false) String role,
      @RequestParam(defaultValue = "false") boolean activeOnly,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserRole userRole = role != null ? UserRole.fromString(role) : null;
      List<User> users = userApplicationService.getAllUsers(userRole, activeOnly, offset, limit);
      return ResponseEntity.ok(users);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{userId}/activate")
  public ResponseEntity<User> activateUser(@PathVariable String userId) {
    try {
      User user = userApplicationService.activateUser(UserId.of(userId));
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{userId}/deactivate")
  public ResponseEntity<User> deactivateUser(@PathVariable String userId) {
    try {
      User user = userApplicationService.deactivateUser(UserId.of(userId));
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class CreateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    // Getters and setters
    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }
  }
}
