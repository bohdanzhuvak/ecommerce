package io.github.bohdanzhuvak.onlinestore.common.auth.security.jwt;

import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    String accessSecret = "01234567890123456789012345678901"; // 32+ chars
    String refreshSecret = "abcdefghijklmnopqrstuvwxyz012345";
    long accessTtl = 60_000; // 1 minute
    long refreshTtl = 3_600_000; // 1 hour
    jwtService = new JwtService(accessSecret, refreshSecret, accessTtl, refreshTtl);
  }

  @Test
  void generateAndValidateAccessToken() {
    String token = jwtService.generateAccessToken("user@example.com", Role.USER, Map.of("k","v"));
    assertTrue(jwtService.isAccessTokenValid(token));
    assertEquals("user@example.com", jwtService.extractSubjectFromAccessToken(token));
    assertEquals(Role.USER, jwtService.extractRole(token));
  }

  @Test
  void invalidAccessToken() {
    assertFalse(jwtService.isAccessTokenValid("bad.token.value"));
  }

  @Test
  void generateAndValidateRefreshToken() {
    String token = jwtService.generateRefreshToken("user@example.com");
    assertTrue(jwtService.isRefreshTokenValid(token));
    assertEquals("user@example.com", jwtService.extractSubjectFromRefreshToken(token));
  }
}


