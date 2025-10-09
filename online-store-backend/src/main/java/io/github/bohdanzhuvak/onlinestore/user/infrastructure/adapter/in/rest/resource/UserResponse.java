package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for user response
 */
public record UserResponse(
    String id,
    String email,
    String firstName,
    String lastName,
    String role,
    boolean active,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static UserResponse from(User user) {
    return new UserResponse(
        user.getId().getValue(),
        user.getEmail().getValue(),
        user.getFirstName(),
        user.getLastName(),
        user.getRole().getValue(),
        user.isActive(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public static List<UserResponse> from(List<User> users) {
    return users.stream()
        .map(UserResponse::from)
        .toList();
  }
}
