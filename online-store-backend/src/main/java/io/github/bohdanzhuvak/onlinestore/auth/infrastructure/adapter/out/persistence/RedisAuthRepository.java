package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthenticationSession;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis implementation of AuthRepository
 */
@Repository
public class RedisAuthRepository implements AuthRepository {

  private static final String SESSION_PREFIX = "auth:session:";
  private static final String REFRESH_TOKEN_PREFIX = "auth:refresh:";
  private static final String USER_SESSIONS_PREFIX = "auth:user:";
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisAuthRepository(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public AuthenticationSession save(AuthenticationSession session) {
    String sessionKey = SESSION_PREFIX + session.getSessionId();
    String refreshTokenKey = REFRESH_TOKEN_PREFIX + session.getSessionId();
    String userSessionsKey = USER_SESSIONS_PREFIX + session.getUserId();

    // Calculate TTL
    Duration ttl = Duration.between(Instant.now(), session.getExpiresAt());

    // Save session
    redisTemplate.opsForValue().set(sessionKey, session, ttl);

    // Save refresh token mapping
    redisTemplate.opsForValue().set(refreshTokenKey, session.getSessionId(), ttl);

    // Add to user sessions set
    redisTemplate.opsForSet().add(userSessionsKey, session.getSessionId());
    redisTemplate.expire(userSessionsKey, ttl);

    return session;
  }

  @Override
  public Optional<AuthenticationSession> findBySessionId(String sessionId) {
    String sessionKey = SESSION_PREFIX + sessionId;
    Object session = redisTemplate.opsForValue().get(sessionKey);

    if (session instanceof AuthenticationSession) {
      return Optional.of((AuthenticationSession) session);
    }

    return Optional.empty();
  }

  @Override
  public Optional<AuthenticationSession> findByRefreshToken(RefreshToken refreshToken) {
    String refreshTokenKey = REFRESH_TOKEN_PREFIX + refreshToken.getValue();
    Object sessionId = redisTemplate.opsForValue().get(refreshTokenKey);

    if (sessionId instanceof String) {
      return findBySessionId((String) sessionId);
    }

    return Optional.empty();
  }

  @Override
  public List<AuthenticationSession> findActiveSessionsByUserId(String userId) {
    String userSessionsKey = USER_SESSIONS_PREFIX + userId;
    Set<Object> sessionIds = redisTemplate.opsForSet().members(userSessionsKey);

    if (sessionIds == null) {
      return List.of();
    }

    return sessionIds.stream()
        .map(Object::toString)
        .map(this::findBySessionId)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(AuthenticationSession::isActive)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteBySessionId(String sessionId) {
    String sessionKey = SESSION_PREFIX + sessionId;
    String refreshTokenKey = REFRESH_TOKEN_PREFIX + sessionId;

    // Get session to find user ID
    Optional<AuthenticationSession> session = findBySessionId(sessionId);
    if (session.isPresent()) {
      String userSessionsKey = USER_SESSIONS_PREFIX + session.get().getUserId();
      redisTemplate.opsForSet().remove(userSessionsKey, sessionId);
    }

    // Delete session and refresh token mapping
    redisTemplate.delete(sessionKey);
    redisTemplate.delete(refreshTokenKey);
  }

  @Override
  public void deleteAllByUserId(String userId) {
    String userSessionsKey = USER_SESSIONS_PREFIX + userId;
    Set<Object> sessionIds = redisTemplate.opsForSet().members(userSessionsKey);

    if (sessionIds != null) {
      for (Object sessionId : sessionIds) {
        deleteBySessionId(sessionId.toString());
      }
    }
  }

  @Override
  public void deleteExpiredSessions() {
    // Redis TTL handles this automatically
    // This method is kept for interface compliance
  }
}
