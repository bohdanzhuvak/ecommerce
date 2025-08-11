package io.github.bohdanzhuvak.onlinestore.common.auth.security.jwt;

import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final SecretKey accessTokenSecretKey;
  private final SecretKey refreshTokenSecretKey;
  private final long accessTokenTtlMillis;
  private final long refreshTokenTtlMillis;

  public JwtService(
      @Value("${security.jwt.access.secret}") String accessSecret,
      @Value("${security.jwt.refresh.secret}") String refreshSecret,
      @Value("${security.jwt.access.ttl-ms}") long accessTokenTtlMillis,
      @Value("${security.jwt.refresh.ttl-ms}") long refreshTokenTtlMillis
  ) {
    this.accessTokenSecretKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
    this.refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
    this.accessTokenTtlMillis = accessTokenTtlMillis;
    this.refreshTokenTtlMillis = refreshTokenTtlMillis;
  }

  public String generateAccessToken(String subjectEmail, Role role, Map<String, Object> extraClaims) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subjectEmail)
        .claims(extraClaims)
        .claim("role", role.name())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(accessTokenTtlMillis)))
        .signWith(accessTokenSecretKey)
        .compact();
  }

  public String generateRefreshToken(String subjectEmail) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subjectEmail)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(refreshTokenTtlMillis)))
        .signWith(refreshTokenSecretKey)
        .compact();
  }

  public boolean isAccessTokenValid(String token) {
    return isTokenValid(token, accessTokenSecretKey);
  }

  public boolean isRefreshTokenValid(String token) {
    return isTokenValid(token, refreshTokenSecretKey);
  }

  public String extractSubjectFromAccessToken(String token) {
    return parseClaims(token, accessTokenSecretKey).getSubject();
  }

  public String extractSubjectFromRefreshToken(String token) {
    return parseClaims(token, refreshTokenSecretKey).getSubject();
  }

  public Role extractRole(String accessToken) {
    Claims claims = parseClaims(accessToken, accessTokenSecretKey);
    String role = claims.get("role", String.class);
    return Role.valueOf(role);
  }

  private boolean isTokenValid(String token, SecretKey key) {
    try {
      parseClaims(token, key);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  private Claims parseClaims(String token, SecretKey key) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}


