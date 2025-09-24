package io.github.bohdanzhuvak.onlinestore.user.application.usecases;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivateUserUseCase {
  private final UserRepository userRepository;

  public ActivateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(UserId userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    user.activate();

    return userRepository.save(user);
  }
}
