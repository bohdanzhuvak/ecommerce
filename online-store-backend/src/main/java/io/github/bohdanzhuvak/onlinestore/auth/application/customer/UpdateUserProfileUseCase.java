package io.github.bohdanzhuvak.onlinestore.auth.application.customer;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
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

    // Update username if provided
    if (command.username() != null) {
      if (userRepository.existsByUsername(command.username())) {
        throw new IllegalArgumentException("Username already exists: " + command.username().getValue());
      }
      user.changeUsername(command.username());
    }

    // Update email if provided
    if (command.email() != null) {
      if (userRepository.existsByEmail(command.email())) {
        throw new IllegalArgumentException("Email already exists: " + command.email().getValue());
      }
      user.changeEmail(command.email());
    }

    // Update password if provided
    if (command.password() != null) {
      user.changePassword(command.password());
    }

    return userRepository.save(user);
  }

  public record UpdateUserProfileCommand(
      UserId userId,
      Username username,
      Email email,
      Password password
  ) {
  }
}
