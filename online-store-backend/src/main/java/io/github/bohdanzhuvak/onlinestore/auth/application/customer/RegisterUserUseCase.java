package io.github.bohdanzhuvak.onlinestore.auth.application.customer;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
  private final UserRepository userRepository;

  public RegisterUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(RegisterUserCommand command) {
    // Check if user already exists
    if (userRepository.existsByEmail(command.email())) {
      throw new IllegalArgumentException("User with email already exists: " + command.email().getValue());
    }
    if (userRepository.existsByUsername(command.username())) {
      throw new IllegalArgumentException("User with username already exists: " + command.username().getValue());
    }

    // Create user
    User user = User.create(
        command.username(),
        command.email(),
        command.password(),
        UserRole.USER
    );

    // Save user
    return userRepository.save(user);
  }

  public record RegisterUserCommand(
      Username username,
      Email email,
      Password password
  ) {
  }
}
