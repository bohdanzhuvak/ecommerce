package io.github.bohdanzhuvak.onlinestore.user.application.admin;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeactivateUserUseCase {
  private final UserRepository userRepository;

  public DeactivateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(UserId userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    user.deactivate();

    return userRepository.save(user);
  }
}
