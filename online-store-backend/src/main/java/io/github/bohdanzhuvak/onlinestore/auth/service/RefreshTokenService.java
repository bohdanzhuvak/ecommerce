package io.github.bohdanzhuvak.onlinestore.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final StringRedisTemplate stringRedisTemplate;

  public void store(String refreshToken, String email, long ttlMillis) {
    stringRedisTemplate.opsForValue().set(refreshKey(refreshToken), email, Duration.ofMillis(ttlMillis));
  }

  public String getEmailByToken(String refreshToken) {
    return stringRedisTemplate.opsForValue().get(refreshKey(refreshToken));
  }

  public void revoke(String refreshToken) {
    stringRedisTemplate.delete(refreshKey(refreshToken));
  }

  private String refreshKey(String token) {
    return "refresh:" + token;
  }
}


