package io.github.bohdanzhuvak.onlinestore.user.application.customer;

import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordUseCase {
  private final UserRepository userRepository;

  public ChangePasswordUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(ChangePasswordCommand command) {
    User user = userRepository.findById(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + command.userId()));

    // Verify current password
    if (!user.getPassword().matches(command.currentPassword())) {
      throw new IllegalArgumentException("Current password is incorrect");
    }

    // Change password
    user.changePassword(Password.fromPlainText(command.newPassword()));

    return userRepository.save(user);
  }

  public record ChangePasswordCommand(
      UserId userId,
      String currentPassword,
      String newPassword
  ) {
  }
}
