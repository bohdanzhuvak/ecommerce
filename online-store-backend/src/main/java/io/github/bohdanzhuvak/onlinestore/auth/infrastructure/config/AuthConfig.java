package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.PasswordHasher;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.application.service.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.LoginUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.LogoutUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.RefreshTokenUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecase.RegisterUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.persistence.RedisAuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.user.UserInfoAdapter;
import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration for authentication services
 */
@Configuration
public class AuthConfig {

  @Bean
  public UserInfoPort userService(ExternalUserOrchestrator externalUserOrchestrator) {
    return new UserInfoAdapter(externalUserOrchestrator);
  }

  @Bean
  public AuthRepository authRepository(RedisTemplate<String, Object> redisTemplate) {
    return new RedisAuthRepository(redisTemplate);
  }

  @Bean
  public LoginUseCase loginUseCase(UserInfoPort userInfoPort,
                                   TokenService tokenService,
                                   AuthRepository authRepository,
                                   PasswordHasher passwordHasher) {
    return new LoginUseCase(userInfoPort, tokenService, authRepository, passwordHasher);
  }

  @Bean
  public RegisterUseCase registerUseCase(UserInfoPort userInfoPort,
                                         TokenService tokenService,
                                         AuthRepository authRepository,
                                         PasswordHasher passwordHasher) {
    return new RegisterUseCase(userInfoPort, tokenService, authRepository, passwordHasher);
  }

  @Bean
  public RefreshTokenUseCase refreshTokenUseCase(TokenService tokenService,
                                                 AuthRepository authRepository,
                                                 UserInfoPort userInfoPort) {
    return new RefreshTokenUseCase(tokenService, authRepository, userInfoPort);
  }

  @Bean
  public LogoutUseCase logoutUseCase(AuthRepository authRepository) {
    return new LogoutUseCase(authRepository);
  }

  @Bean
  public AuthApplicationService authApplicationService(LoginUseCase loginUseCase,
                                                       RegisterUseCase registerUseCase,
                                                       RefreshTokenUseCase refreshTokenUseCase,
                                                       LogoutUseCase logoutUseCase) {
    return new AuthApplicationService(loginUseCase, registerUseCase, refreshTokenUseCase, logoutUseCase);
  }
}
