package io.github.bohdanzhuvak.onlinestore.common.auth;

import io.github.bohdanzhuvak.onlinestore.common.dto.AuthResponse;
import io.github.bohdanzhuvak.onlinestore.common.dto.LoginRequest;
import io.github.bohdanzhuvak.onlinestore.common.dto.RegisterRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@NoArgsConstructor
public class AuthController {
  @GetMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest loginRequest) {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/register")
  public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
    throw new UnsupportedOperationException();
  }
}
