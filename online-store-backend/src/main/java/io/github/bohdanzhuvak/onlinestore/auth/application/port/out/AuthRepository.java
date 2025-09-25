package io.github.bohdanzhuvak.onlinestore.auth.application.port.out;

import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for authentication-related operations
 */
public interface AuthRepository {

  /**
   * Saves an authentication session
   *
   * @param session the session to save
   * @return the saved session
   */
  AuthenticationSession save(AuthenticationSession session);

  /**
   * Finds an authentication session by session ID
   *
   * @param sessionId the session ID
   * @return the session if found, empty otherwise
   */
  Optional<AuthenticationSession> findBySessionId(String sessionId);

  /**
   * Finds an authentication session by refresh token
   *
   * @param refreshToken the refresh token
   * @return the session if found, empty otherwise
   */
  Optional<AuthenticationSession> findByRefreshToken(RefreshToken refreshToken);

  /**
   * Finds active sessions for a user
   *
   * @param userId the user ID
   * @return list of active sessions for the user
   */
  List<AuthenticationSession> findActiveSessionsByUserId(String userId);

  /**
   * Deletes an authentication session
   *
   * @param sessionId the session ID to delete
   */
  void deleteBySessionId(String sessionId);

  /**
   * Deletes all sessions for a user
   *
   * @param userId the user ID
   */
  void deleteAllByUserId(String userId);

  /**
   * Deletes expired sessions
   */
  void deleteExpiredSessions();
}
