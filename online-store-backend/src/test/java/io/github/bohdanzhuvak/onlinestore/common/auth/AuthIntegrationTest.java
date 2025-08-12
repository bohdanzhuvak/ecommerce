package io.github.bohdanzhuvak.onlinestore.common.auth;

import io.github.bohdanzhuvak.onlinestore.common.model.Role;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AuthIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Container
  static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.data.redis.host", () -> redis.getHost());
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    registry.add("security.jwt.access.secret", () -> "01234567890123456789012345678901");
    registry.add("security.jwt.refresh.secret", () -> "abcdefghijklmnopqrstuvwxyz012345");
    registry.add("security.jwt.access.ttl-ms", () -> 120000);
    registry.add("security.jwt.refresh.ttl-ms", () -> 3600000);
  }

  @Autowired MockMvc mockMvc;
  @Autowired UserRepository userRepository;
  @Autowired PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() {
    userRepository.deleteAll();
    User u = new User();
    u.setUsername("Test");
    u.setEmail("user@example.com");
    u.setPassword(passwordEncoder.encode("password"));
    u.setRole(Role.USER);
    userRepository.save(u);
  }

  @Test
  void loginReturnsTokens() throws Exception {
    String body = "{\"email\":\"user@example.com\",\"password\":\"password\"}";
    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(header().exists(HttpHeaders.SET_COOKIE));
  }

  @Test
  void registerReturnsTokens() throws Exception {
    String body = "{\"username\":\"new\",\"email\":\"new@example.com\",\"password\":\"password\"}";
    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(header().exists("Set-Cookie"));
  }

  @Test
  void refreshWithCookieWorks() throws Exception {
    // login to get cookie
    String loginBody = "{\"email\":\"user@example.com\",\"password\":\"password\"}";
    var mvcResult = mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginBody))
        .andExpect(status().isOk())
        .andReturn();

    // Extract refresh cookie from response
    jakarta.servlet.http.Cookie[] cookies = mvcResult.getResponse().getCookies();
    Cookie refreshCookie = null;
    for (Cookie c : cookies) {
      if ("refreshToken".equals(c.getName())) {
        refreshCookie = c;
        break;
      }
    }

    org.junit.jupiter.api.Assertions.assertNotNull(refreshCookie, "refreshToken cookie should be present");

    mockMvc.perform(post("/api/v1/auth/refresh").cookie(refreshCookie))
        .andExpect(status().isOk())
        .andExpect(header().exists("Set-Cookie"));
  }
}


