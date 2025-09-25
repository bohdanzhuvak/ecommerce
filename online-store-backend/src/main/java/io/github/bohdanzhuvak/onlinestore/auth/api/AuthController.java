package io.github.bohdanzhuvak.onlinestore.auth.api;

import io.github.bohdanzhuvak.onlinestore.auth.api.dto.AuthResponse;
import io.github.bohdanzhuvak.onlinestore.auth.api.dto.LoginRequest;
import io.github.bohdanzhuvak.onlinestore.auth.api.dto.RegisterRequest;
import io.github.bohdanzhuvak.onlinestore.auth.application.result.AuthenticationResult;
import io.github.bohdanzhuvak.onlinestore.auth.application.service.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenPair;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication operations
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthApplicationService authApplicationService;

  public AuthController(AuthApplicationService authApplicationService) {
    this.authApplicationService = authApplicationService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    AuthenticationResult result = authApplicationService.login(request.email(), request.password());
    AuthResponse response = buildAuthResponse(result);
    ResponseCookie cookie = buildRefreshCookie(result.tokenPair().getRefreshToken());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    AuthenticationResult result = authApplicationService.register(
        request.email(), request.password(), request.firstName(), request.lastName());
    AuthResponse response = buildAuthResponse(result);
    ResponseCookie cookie = buildRefreshCookie(result.tokenPair().getRefreshToken());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@CookieValue(value = "refreshToken", required = false) String refreshTokenValue) {
    if (refreshTokenValue == null) {
      return ResponseEntity.status(401).build();
    }
    AuthenticationResult result = authApplicationService.refreshToken(refreshTokenValue);
    AuthResponse response = buildAuthResponse(result);
    ResponseCookie cookie = buildRefreshCookie(result.tokenPair().getRefreshToken());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken", required = false) String refreshTokenValue) {
    if (refreshTokenValue != null) {
      try {
        authApplicationService.logout(refreshTokenValue);
      } catch (Exception e) {
        // Log error but don't fail the request
      }
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

  private AuthResponse buildAuthResponse(AuthenticationResult result) {
    TokenPair tokenPair = result.tokenPair();
    User user = result.user();
    return new AuthResponse(
        tokenPair.getAccessToken().getValue(),
        new AuthResponse.UserInfo(
            user.getId().getValue(),
            user.getEmail().getValue(),
            user.getRole().getValue()
        ),
        new AuthResponse.TokenInfo(
            tokenPair.getAccessToken().getExpiresAt().toEpochMilli(),
            tokenPair.getRefreshToken().getExpiresAt().toEpochMilli()
        )
    );
  }

  private ResponseCookie buildRefreshCookie(RefreshToken refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken.getValue())
        .httpOnly(true)
        .secure(false)
        .sameSite("Lax")
        .path("/api/v1/auth")
        .maxAge(60 * 60 * 24 * 7)
        .build();
  }
}
