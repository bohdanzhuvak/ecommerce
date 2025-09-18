package io.github.bohdanzhuvak.onlinestore.user.application.customer;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserProfileUseCase {
  private final UserRepository userRepository;

  public UpdateUserProfileUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(UpdateUserProfileCommand command) {
    User user = userRepository.findById(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + command.userId()));

    user.updateProfile(command.firstName(), command.lastName());

    return userRepository.save(user);
  }

  public record UpdateUserProfileCommand(
      UserId userId,
      String firstName,
      String lastName
  ) {
  }
}
