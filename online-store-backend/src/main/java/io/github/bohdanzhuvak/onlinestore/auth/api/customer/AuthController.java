package io.github.bohdanzhuvak.onlinestore.auth.api.customer;

import io.github.bohdanzhuvak.onlinestore.auth.application.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AccessToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.RefreshToken;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthApplicationService authApplicationService;
  private final long refreshTtlMillis;
  private final long accessTtlMillis;

  public AuthController(AuthApplicationService authApplicationService,
                        @Value("${security.jwt.refresh.ttl-ms}") long refreshTtlMillis,
                        @Value("${security.jwt.access.ttl-ms}") long accessTtlMillis) {
    this.authApplicationService = authApplicationService;
    this.refreshTtlMillis = refreshTtlMillis;
    this.accessTtlMillis = accessTtlMillis;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    try {
      Email email = Email.of(request.getEmail());
      Password password = Password.ofHashed(request.getPassword()); // In real implementation, this would be hashed

      Optional<User> userOpt = authApplicationService.loginUser(email, password);
      if (userOpt.isEmpty()) {
        return ResponseEntity.status(401).build();
      }

      User user = userOpt.get();
      AccessToken accessToken = authApplicationService.generateAccessToken(user, accessTtlMillis);
      RefreshToken refreshToken = authApplicationService.generateRefreshToken(user, refreshTtlMillis);

      AuthResponse response = buildAuthResponse(user, accessToken);
      ResponseCookie cookie = buildRefreshCookie(refreshToken);

      return ResponseEntity.ok()
          .header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    try {
      Username username = Username.of(request.getUsername());
      Email email = Email.of(request.getEmail());
      Password password = Password.ofHashed(request.getPassword()); // In real implementation, this would be hashed

      User user = authApplicationService.registerUser(username, email, password);
      AccessToken accessToken = authApplicationService.generateAccessToken(user, accessTtlMillis);
      RefreshToken refreshToken = authApplicationService.generateRefreshToken(user, refreshTtlMillis);

      AuthResponse response = buildAuthResponse(user, accessToken);
      ResponseCookie cookie = buildRefreshCookie(refreshToken);

      return ResponseEntity.ok()
          .header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
    if (refreshToken == null) {
      return ResponseEntity.status(401).build();
    }

    try {
      // In real implementation, this would validate the refresh token
      // For now, we'll just return 401
      return ResponseEntity.status(401).build();
    } catch (Exception e) {
      return ResponseEntity.status(401).build();
    }
  }

  @GetMapping("/me")
  public ResponseEntity<AuthResponse.UserInfo> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    try {
      UserId userId = UserId.of(userPrincipal.getEmail()); // This should be user ID, not email
      Optional<User> userOpt = authApplicationService.getUser(userId);
      if (userOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      User user = userOpt.get();
      AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
      userInfo.setId(user.getId().getValue());
      userInfo.setUsername(user.getUsername().getValue());
      userInfo.setEmail(user.getEmail().getValue());
      userInfo.setRole(user.getRole().getValue());

      return ResponseEntity.ok(userInfo);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
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

  private AuthResponse buildAuthResponse(User user, AccessToken accessToken) {
    AuthResponse response = new AuthResponse();
    response.setToken(accessToken.getValue());
    response.setRole(user.getRole().getValue());

    // User info
    AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
    userInfo.setId(user.getId().getValue());
    userInfo.setUsername(user.getUsername().getValue());
    userInfo.setEmail(user.getEmail().getValue());
    userInfo.setRole(user.getRole().getValue());
    response.setUser(userInfo);

    // Token info
    AuthResponse.TokenInfo tokenInfo = new AuthResponse.TokenInfo();
    Instant now = Instant.now();
    tokenInfo.setExpiresAt(accessToken.getExpiresAt().toEpochMilli());
    tokenInfo.setRefreshExpiresAt(now.plusMillis(refreshTtlMillis).toEpochMilli());
    response.setTokenInfo(tokenInfo);

    return response;
  }

  private ResponseCookie buildRefreshCookie(RefreshToken refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken.getValue())
        .httpOnly(true)
        .secure(false)
        .sameSite("Lax")
        .path("/api/v1/auth")
        .maxAge(refreshTtlMillis / 1000)
        .build();
  }

  // DTO classes for requests
  public static class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class AuthResponse {
    private String token;
    private String role;
    private UserInfo user;
    private TokenInfo tokenInfo;

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }

    public UserInfo getUser() {
      return user;
    }

    public void setUser(UserInfo user) {
      this.user = user;
    }

    public TokenInfo getTokenInfo() {
      return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
      this.tokenInfo = tokenInfo;
    }

    public static class UserInfo {
      private String id;
      private String username;
      private String email;
      private String role;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getUsername() {
        return username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public String getEmail() {
        return email;
      }

      public void setEmail(String email) {
        this.email = email;
      }

      public String getRole() {
        return role;
      }

      public void setRole(String role) {
        this.role = role;
      }
    }

    public static class TokenInfo {
      private long expiresAt;
      private long refreshExpiresAt;

      public long getExpiresAt() {
        return expiresAt;
      }

      public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
      }

      public long getRefreshExpiresAt() {
        return refreshExpiresAt;
      }

      public void setRefreshExpiresAt(long refreshExpiresAt) {
        this.refreshExpiresAt = refreshExpiresAt;
      }
    }
  }

  // Placeholder classes for current user and user principal
  public static class CurrentUser {
    // This would be a Spring Security annotation
  }

  public static class UserPrincipal {
    private String email;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}
