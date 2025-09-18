package io.github.bohdanzhuvak.onlinestore.user.api.customer;

import io.github.bohdanzhuvak.onlinestore.user.application.UserApplicationService;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/users")
public class UserController {
  private final UserApplicationService userApplicationService;

  public UserController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
    try {
      User user = userApplicationService.registerUser(
          Email.of(request.getEmail()),
          request.getPassword(),
          request.getFirstName(),
          request.getLastName()
      );
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
    Optional<User> user = userApplicationService.getUserProfile(UserId.of(userId));
    return user.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<User> updateProfile(@PathVariable String userId,
                                            @RequestBody UpdateProfileRequest request) {
    try {
      User user = userApplicationService.updateUserProfile(
          UserId.of(userId),
          request.getFirstName(),
          request.getLastName()
      );
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{userId}/password")
  public ResponseEntity<User> changePassword(@PathVariable String userId,
                                             @RequestBody ChangePasswordRequest request) {
    try {
      User user = userApplicationService.changePassword(
          UserId.of(userId),
          request.getCurrentPassword(),
          request.getNewPassword()
      );
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

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
  }

  public static class UpdateProfileRequest {
    private String firstName;
    private String lastName;

    // Getters and setters
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
  }

  public static class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

    // Getters and setters
    public String getCurrentPassword() {
      return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
      this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
      return newPassword;
    }

    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }
  }
}
