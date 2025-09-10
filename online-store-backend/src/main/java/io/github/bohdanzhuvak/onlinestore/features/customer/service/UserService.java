package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.common.service.AbstractBaseService;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BaseRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * User service implementation using BaseServiceImpl.
 * This shows how to implement a service for specific entity types.
 */
@Service
@RequiredArgsConstructor
public class UserService extends AbstractBaseService<User, Long> {

  private final UserRepository userRepository;

  @Override
  protected BaseRepository<User, Long> getRepository() {
    return userRepository;
  }
}
