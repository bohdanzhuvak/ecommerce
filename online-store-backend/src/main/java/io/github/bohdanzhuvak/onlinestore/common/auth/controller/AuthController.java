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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole(), Map.of("username", user.getUsername()));
    String refreshToken = jwtService.generateRefreshToken(user.getEmail());
    AuthResponse response = new AuthResponse();
    response.setToken(accessToken);
    response.setRole(user.getRole());
    refreshTokenService.store(refreshToken, user.getEmail(), refreshTtlMillis);
    return ResponseEntity.ok().header("X-Refresh-Token", refreshToken).body(response);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);
    userRepository.save(user);
    String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole(), Map.of("username", user.getUsername()));
    String refreshToken = jwtService.generateRefreshToken(user.getEmail());
    AuthResponse response = new AuthResponse();
    response.setToken(accessToken);
    response.setRole(user.getRole());
    refreshTokenService.store(refreshToken, user.getEmail(), refreshTtlMillis);
    return ResponseEntity.ok().header("X-Refresh-Token", refreshToken).body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
    if (!jwtService.isRefreshTokenValid(refreshToken)) {
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
    AuthResponse response = new AuthResponse();
    response.setToken(newAccess);
    response.setRole(user.getRole());
    return ResponseEntity.ok().header("X-Refresh-Token", newRefresh).body(response);
  }

  
}


