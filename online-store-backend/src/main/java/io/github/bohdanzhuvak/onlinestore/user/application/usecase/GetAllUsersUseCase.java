package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;

import java.util.List;

@UseCase
public class GetAllUsersUseCase {
  private final UserRepository userRepository;

  public GetAllUsersUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> execute(GetAllUsersCommand command) {
    if (command.role() != null) {
      return userRepository.findByRole(command.role(), command.offset(), command.limit());
    } else if (command.activeOnly()) {
      return userRepository.findActiveUsers(command.offset(), command.limit());
    } else {
      return userRepository.findAll(command.offset(), command.limit());
    }
  }

  public record GetAllUsersCommand(
      UserRole role,
      boolean activeOnly,
      int offset,
      int limit
  ) {
  }
}
