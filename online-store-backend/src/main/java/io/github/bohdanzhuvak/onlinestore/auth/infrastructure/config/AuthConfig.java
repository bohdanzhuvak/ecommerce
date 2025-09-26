package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.auth.application.service.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.LoginUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.LogoutUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.RefreshTokenUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.RegisterUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for authentication services
 */
@Configuration
public class AuthConfig {

  @Bean
  public AuthApplicationService authApplicationService(LoginUseCase loginUseCase,
                                                       RegisterUseCase registerUseCase,
                                                       RefreshTokenUseCase refreshTokenUseCase,
                                                       LogoutUseCase logoutUseCase) {
    return new AuthApplicationService(loginUseCase, registerUseCase, refreshTokenUseCase, logoutUseCase);
  }
}
