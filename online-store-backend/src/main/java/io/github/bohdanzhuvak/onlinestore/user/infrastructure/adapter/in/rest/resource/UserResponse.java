package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for user response
 */
public record UserResponse(
    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "Email address", requiredMode = Schema.RequiredMode.REQUIRED)
    String email,

    @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED)
    String firstName,

    @Schema(description = "Last name", requiredMode = Schema.RequiredMode.REQUIRED)
    String lastName,

    @Schema(description = "User role", requiredMode = Schema.RequiredMode.REQUIRED)
    String role,

    @Schema(description = "Whether user is active", requiredMode = Schema.RequiredMode.REQUIRED)
    boolean active,

    @Schema(description = "Creation timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
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
