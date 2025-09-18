package io.github.bohdanzhuvak.onlinestore.user.application.customer;

import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
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
      throw new IllegalArgumentException("User with email already exists: " + command.email());
    }

    // Create new user
    User user = new User(
        UserId.generate(),
        command.email(),
        Password.fromPlainText(command.password()),
        command.firstName(),
        command.lastName(),
        UserRole.CUSTOMER
    );

    return userRepository.save(user);
  }

  public record RegisterUserCommand(
      Email email,
      String password,
      String firstName,
      String lastName
  ) {
  }
}
