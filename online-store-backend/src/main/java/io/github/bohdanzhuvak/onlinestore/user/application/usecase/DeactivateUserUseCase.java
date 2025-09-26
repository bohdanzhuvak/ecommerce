package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
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
