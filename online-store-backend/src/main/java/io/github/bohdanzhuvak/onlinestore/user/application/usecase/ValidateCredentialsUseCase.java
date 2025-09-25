package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import org.springframework.stereotype.Service;

@Service
public class ValidateCredentialsUseCase {
  private final UserRepository userRepository;

  public ValidateCredentialsUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean execute(Email email, Password password) {
    return userRepository.findByEmail(email)
        .map(user -> user.isPasswordValid(password))
        .orElse(false);
  }
}
