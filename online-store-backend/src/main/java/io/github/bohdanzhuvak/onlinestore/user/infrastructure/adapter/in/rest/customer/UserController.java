package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.user.application.service.WebUserOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UpdateProfileRequest;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/users")
public class UserController {
  private final WebUserOrchestratorService webUserOrchestratorService;

  public UserController(WebUserOrchestratorService webUserOrchestratorService) {
    this.webUserOrchestratorService = webUserOrchestratorService;
  }

  @GetMapping
  public ResponseEntity<UserResponse> getUserProfile(@CurrentUserId String userId) {
    User user = webUserOrchestratorService.getUserProfile(UserId.of(userId));
    return ResponseEntity.ok(UserResponse.from(user));
  }

  @PutMapping("profile")
  public ResponseEntity<UserResponse> updateProfile(@CurrentUserId String userId,
                                            @RequestBody UpdateProfileRequest request) {
    try {
      User user = webUserOrchestratorService.updateUserProfile(
          UserId.of(userId),
          request.firstName(),
          request.lastName()
      );
      return ResponseEntity.ok(UserResponse.from(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
