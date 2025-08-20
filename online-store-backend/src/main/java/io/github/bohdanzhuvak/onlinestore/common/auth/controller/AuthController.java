package io.github.bohdanzhuvak.onlinestore.common.auth.controller;

import io.github.bohdanzhuvak.onlinestore.common.auth.security.jwt.JwtService;
import io.github.bohdanzhuvak.onlinestore.common.auth.service.RefreshTokenService;
import io.github.bohdanzhuvak.onlinestore.common.dto.AuthResponse;
import io.github.bohdanzhuvak.onlinestore.common.dto.LoginRequest;
import io.github.bohdanzhuvak.onlinestore.common.dto.RegisterRequest;
import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @org.springframework.beans.factory.annotation.Value("${security.jwt.refresh.ttl-ms}")
  private long refreshTtlMillis;
  @org.springframework.beans.factory.annotation.Value("${security.jwt.access.ttl-ms}")
  private long accessTtlMillis;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole(), Map.of("username", user.getUsername()));
    String refreshToken = jwtService.generateRefreshToken(user.getEmail());

    AuthResponse response = buildAuthResponse(user, accessToken);
    refreshTokenService.store(refreshToken, user.getEmail(), refreshTtlMillis);

    ResponseCookie cookie = buildRefreshCookie(refreshToken);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);
    user = userRepository.save(user);

    String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole(), Map.of("username", user.getUsername()));
    String refreshToken = jwtService.generateRefreshToken(user.getEmail());

    AuthResponse response = buildAuthResponse(user, accessToken);
    refreshTokenService.store(refreshToken, user.getEmail(), refreshTtlMillis);

    ResponseCookie cookie = buildRefreshCookie(refreshToken);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
    if (refreshToken == null || !jwtService.isRefreshTokenValid(refreshToken)) {
      return ResponseEntity.status(401).build();
    }
    String email = refreshTokenService.getEmailByToken(refreshToken);
    if (email == null) {
      return ResponseEntity.status(401).build();
    }
    User user = userRepository.findByEmail(email).orElseThrow();
    String newAccess = jwtService.generateAccessToken(user.getEmail(), user.getRole(), Map.of("username", user.getUsername()));
    String newRefresh = jwtService.generateRefreshToken(user.getEmail());

    refreshTokenService.revoke(refreshToken);
    refreshTokenService.store(newRefresh, user.getEmail(), refreshTtlMillis);

    AuthResponse response = buildAuthResponse(user, newAccess);
    ResponseCookie cookie = buildRefreshCookie(newRefresh);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @GetMapping("/me")
  public ResponseEntity<AuthResponse.UserInfo> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow();

    AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
    userInfo.setId(user.getId());
    userInfo.setUsername(user.getUsername());
    userInfo.setEmail(user.getEmail());
    userInfo.setRole(user.getRole());

    return ResponseEntity.ok(userInfo);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
    if (refreshToken != null) {
      refreshTokenService.revoke(refreshToken);
    }

    ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
        .httpOnly(true)
        .secure(false)
        .sameSite("Lax")
        .path("/api/v1/auth")
        .maxAge(0)
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .build();
  }

  private AuthResponse buildAuthResponse(User user, String accessToken) {
    AuthResponse response = new AuthResponse();
    response.setToken(accessToken);
    response.setRole(user.getRole());

    // User info
    AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
    userInfo.setId(user.getId());
    userInfo.setUsername(user.getUsername());
    userInfo.setEmail(user.getEmail());
    userInfo.setRole(user.getRole());
    response.setUser(userInfo);

    // Token info
    AuthResponse.TokenInfo tokenInfo = new AuthResponse.TokenInfo();
    Instant now = Instant.now();
    tokenInfo.setExpiresAt(now.plusMillis(accessTtlMillis).toEpochMilli());
    tokenInfo.setRefreshExpiresAt(now.plusMillis(refreshTtlMillis).toEpochMilli());
    response.setTokenInfo(tokenInfo);

    return response;
  }

  private ResponseCookie buildRefreshCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(false)
        .sameSite("Lax")
        .path("/api/v1/auth")
        .maxAge(refreshTtlMillis / 1000)
        .build();
  }
}


