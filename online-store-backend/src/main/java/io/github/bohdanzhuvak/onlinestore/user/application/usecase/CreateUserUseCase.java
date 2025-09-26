package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;

@UseCase
public class CreateUserUseCase {
  private final UserRepository userRepository;

  public CreateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(CreateUserCommand command) {
    // Check if user already exists
    if (userRepository.existsByEmail(command.email())) {
      throw new IllegalArgumentException("User with email already exists: " + command.email());
    }

    // Create new user
    User user = new User(
        UserId.generate(),
        command.email(),
        command.password(),
        command.firstName(),
        command.lastName(),
        UserRole.CUSTOMER
    );

    return userRepository.save(user);
  }

  public record CreateUserCommand(
      Email email,
      Password password,
      String firstName,
      String lastName
  ) {
  }
}
