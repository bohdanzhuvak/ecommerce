package io.github.bohdanzhuvak.onlinestore.auth.application.customer;

import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserUseCase {
  private final UserRepository userRepository;

  public GetUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> execute(UserId userId) {
    return userRepository.findById(userId);
  }
}
