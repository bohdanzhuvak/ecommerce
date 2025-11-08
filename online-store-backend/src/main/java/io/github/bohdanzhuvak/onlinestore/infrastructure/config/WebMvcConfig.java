package io.github.bohdanzhuvak.onlinestore.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.authorization.CurrentUserIdResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration for Spring MVC customizations
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final CurrentUserIdResolver currentUserIdResolver;

  public WebMvcConfig(CurrentUserIdResolver currentUserIdResolver) {
    this.currentUserIdResolver = currentUserIdResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(currentUserIdResolver);
  }
}