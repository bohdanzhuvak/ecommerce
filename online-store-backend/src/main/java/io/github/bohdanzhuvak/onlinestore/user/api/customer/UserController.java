package io.github.bohdanzhuvak.onlinestore.user.api.customer;

import io.github.bohdanzhuvak.onlinestore.user.application.service.UserApplicationService;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/users")
public class UserController {
  private final UserApplicationService userApplicationService;

  public UserController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
    User user = userApplicationService.getUserProfile(UserId.of(userId));
    return ResponseEntity.ok(user);
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

}
