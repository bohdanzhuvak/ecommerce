package io.github.bohdanzhuvak.onlinestore.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "security.cors")
public class CorsProperties {
  private List<String> allowedOrigins;
}
