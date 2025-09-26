package io.github.bohdanzhuvak.onlinestore.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "io.github.bohdanzhuvak.onlinestore",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = UseCase.class)
)
public class DomainConfig {
}
