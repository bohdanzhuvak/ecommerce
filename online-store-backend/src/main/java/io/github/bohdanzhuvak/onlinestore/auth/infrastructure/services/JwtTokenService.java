package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.services;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AccessToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * JWT implementation of TokenService
 */
@Service
public class JwtTokenService implements TokenService {

  private final SecretKey accessTokenSecretKey;
  private final SecretKey refreshTokenSecretKey;
  private final String issuer;
  private final long refreshTokenTtlMillis;
  private final long accessTokenTtlMillis;

  public JwtTokenService(@Value("${security.jwt.issuer:online-store}") String issuer,
                         @Value("${security.jwt.access.secret}") String accessTokenSecretKey,
                         @Value("${security.jwt.refresh.secret}") String refreshTokenSecretKey,
                         @Value("${security.jwt.access.ttl-ms}") long accessTokenTtlMillis,
                         @Value("${security.jwt.refresh.ttl-ms}") long refreshTokenTtlMillis) {
    this.accessTokenSecretKey = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
    this.refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
    this.issuer = issuer;
    this.accessTokenTtlMillis = accessTokenTtlMillis;
    this.refreshTokenTtlMillis = refreshTokenTtlMillis;
  }

  @Override
  public TokenPair generateTokenPair(User user) {

    AccessToken accessToken = generateAccessToken(
        user.getId(),
        user.getEmail(),
        user.getRole()
    );

    RefreshToken refreshToken = generateRefreshToken(
        user.getId(),
        user.getEmail()
    );

    return TokenPair.of(accessToken, refreshToken);
  }

  private AccessToken generateAccessToken(UserId userId, Email email, UserRole role) {
    Instant now = Instant.now();
    Instant expiresAt = now.plusMillis(accessTokenTtlMillis);

    String token = Jwts.builder()
        .subject(userId.getValue())
        .issuer(issuer)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiresAt))
        .claim("email", email.getValue())
        .claim("role", role.getValue())
        .signWith(accessTokenSecretKey)
        .compact();

    return AccessToken.of(token, expiresAt);
  }

  private RefreshToken generateRefreshToken(UserId userId, Email email) {
    Instant now = Instant.now();
    Instant expiresAt = now.plusMillis(refreshTokenTtlMillis);

    String token = Jwts.builder()
        .subject(userId.getValue())
        .issuer(issuer)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiresAt))
        .claim("email", email.getValue())
        .claim("type", "refresh")
        .signWith(refreshTokenSecretKey)
        .compact();

    return RefreshToken.of(token, expiresAt);
  }

  @Override
  public boolean isAccessTokenValid(AccessToken token) {
    return isTokenValid(token.getValue(), accessTokenSecretKey);
  }

  @Override
  public boolean isAccessTokenValid(String tokenValue) {
    return isTokenValid(tokenValue, accessTokenSecretKey);
  }

  @Override
  public boolean isRefreshTokenValid(RefreshToken token) {
    return isTokenValid(token.getValue(), refreshTokenSecretKey);
  }

  private String extractSubjectFromAccessToken(String token) {
    return parseClaims(token, accessTokenSecretKey).getSubject();
  }

  private String extractSubjectFromRefreshToken(String token) {
    return parseClaims(token, refreshTokenSecretKey).getSubject();
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

  @Override
  public Optional<UserInfo> extractUserInfo(AccessToken token) {
    return extractUserInfo(token.getValue());
  }

  @Override
  public Optional<UserInfo> extractUserInfo(String tokenValue) {
    try {
      Claims claims = parseClaims(tokenValue, accessTokenSecretKey);

      if ("refresh".equals(claims.get("type"))) {
        return Optional.empty();
      }

      UserInfo userInfo = new UserInfo(
          UserId.of(claims.getSubject()),
          Email.of(claims.get("email", String.class)),
          UserRole.fromString(claims.get("role", String.class))
      );

      return Optional.of(userInfo);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<UserInfo> extractUserInfo(RefreshToken token) {
    try {
      Claims claims = parseClaims(token.getValue(), refreshTokenSecretKey);

      if (!"refresh".equals(claims.get("type"))) {
        return Optional.empty();
      }

      UserInfo userInfo = new UserInfo(
          UserId.of(claims.getSubject()),
          Email.of(claims.get("email", String.class)),
          UserRole.CUSTOMER
      );

      return Optional.of(userInfo);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
