package io.github.bohdanzhuvak.onlinestore.auth.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate root representing an authentication session
 */
public final class AuthenticationSession {
  private final String sessionId;
  private final UserId userId;
  private final Email email;
  private final UserRole role;
  private final Instant createdAt;
  private final Instant expiresAt;
  private boolean active;

  private AuthenticationSession(String sessionId, UserId userId, Email email, UserRole role,
                                Instant createdAt, Instant expiresAt, boolean active) {
    if (sessionId == null || sessionId.trim().isEmpty()) {
      throw new IllegalArgumentException("Session ID cannot be null or empty");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (role == null) {
      throw new IllegalArgumentException("Role cannot be null or empty");
    }
    if (createdAt == null) {
      throw new IllegalArgumentException("Created at cannot be null");
    }
    if (expiresAt == null) {
      throw new IllegalArgumentException("Expires at cannot be null");
    }
    if (expiresAt.isBefore(createdAt)) {
      throw new IllegalArgumentException("Expires at cannot be before created at");
    }

    this.sessionId = sessionId;
    this.userId = userId;
    this.email = email;
    this.role = role;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.active = active;
  }

  public static AuthenticationSession create(User user) {
    Instant now = Instant.now();
    Instant expiresAt = now.plusMillis(1000);
    String sessionId = generateSessionId(user.getId(), now);

    return new AuthenticationSession(sessionId, user.getId(), user.getEmail(), user.getRole(), now, expiresAt, true);
  }

  public static AuthenticationSession restore(String sessionId, String userId, String email,
                                              String role, Instant createdAt, Instant expiresAt, boolean active) {
    return new AuthenticationSession(sessionId, UserId.of(userId), Email.of(email), UserRole.fromString(role), createdAt, expiresAt, active);
  }

  private static String generateSessionId(UserId userId, Instant timestamp) {
    return "session_" + userId + "_" + timestamp.toEpochMilli();
  }

  // Business methods
  public void terminate() {
    this.active = false;
  }

  public void extend(long additionalTtlMillis) {
    if (!active) {
      throw new IllegalStateException("Cannot extend terminated session");
    }
    // In a real implementation, this would update the expiresAt field
    // For now, we'll just validate the state
  }

  public boolean isExpired() {
    return Instant.now().isAfter(expiresAt);
  }

  public boolean isActive() {
    return active && !isExpired();
  }

  public boolean belongsTo(String userId) {
    return this.userId.equals(userId);
  }

  // Getters
  public String getSessionId() {
    return sessionId;
  }

  public UserId getUserId() {
    return userId;
  }

  public Email getEmail() {
    return email;
  }

  public UserRole getRole() {
    return role;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthenticationSession that = (AuthenticationSession) o;
    return Objects.equals(sessionId, that.sessionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionId);
  }

  @Override
  public String toString() {
    return "AuthenticationSession{" +
        "sessionId='" + sessionId + '\'' +
        ", userId='" + userId + '\'' +
        ", email='" + email + '\'' +
        ", role='" + role + '\'' +
        ", active=" + active +
        ", expiresAt=" + expiresAt +
        '}';
  }
}
