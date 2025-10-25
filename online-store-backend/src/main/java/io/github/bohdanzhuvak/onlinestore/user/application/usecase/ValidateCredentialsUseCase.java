package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
public class ValidateCredentialsUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public ValidateCredentialsUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean execute(Email email, String rawPassword) {
    return userRepository.findByEmail(email)
        .map(user -> passwordEncoder.matches(rawPassword, user.getPassword().getHashedValue()))
        .orElse(false);
  }
}
