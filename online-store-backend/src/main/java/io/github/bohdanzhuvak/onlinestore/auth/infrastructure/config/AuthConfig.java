package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.auth.application.ports.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.application.services.AuthApplicationService;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.LoginUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.LogoutUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.RefreshTokenUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.application.usecases.RegisterUseCase;
import io.github.bohdanzhuvak.onlinestore.auth.domain.AuthRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.PasswordHasher;
import io.github.bohdanzhuvak.onlinestore.auth.domain.TokenService;
import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.adapters.external.UserInfoAdapter;
import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.adapters.persistence.RedisAuthRepository;
import io.github.bohdanzhuvak.onlinestore.user.application.ports.UserQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration for authentication services
 */
@Configuration
public class AuthConfig {

  @Bean
  public UserInfoPort userService(UserQuery userQuery) {
    return new UserInfoAdapter(userQuery);
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
