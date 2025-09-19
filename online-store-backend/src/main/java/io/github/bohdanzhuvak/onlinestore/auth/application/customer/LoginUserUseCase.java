package io.github.bohdanzhuvak.onlinestore.auth.application.customer;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserUseCase {
  private final UserRepository userRepository;

  public LoginUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> execute(LoginUserCommand command) {
    return userRepository.findByEmail(command.email())
        .filter(user -> user.isActive())
        .filter(user -> user.getPassword().getHashedValue().equals(command.password().getHashedValue()));
  }

  public record LoginUserCommand(
      Email email,
      Password password
  ) {
  }
}
