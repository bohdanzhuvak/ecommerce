package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
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

  public PageResult<User> execute(GetAllUsersCommand command) {
    // React-admin sends 1-based page numbers, convert to 0-based offset
    int offset = (command.page() - 1) * command.pageSize();
    List<User> users;
    long total;

    if (command.role() != null) {
      users = userRepository.findByRole(command.role(), offset, command.pageSize());
      total = userRepository.countByRole(command.role());
    } else if (command.activeOnly()) {
      users = userRepository.findActiveUsers(offset, command.pageSize());
      total = userRepository.countActiveUsers();
    } else {
      users = userRepository.findAll(offset, command.pageSize());
      total = userRepository.count();
    }

    return PageResult.of(users, total, command.page(), command.pageSize());
  }

  public record GetAllUsersCommand(
      UserRole role,
      boolean activeOnly,
      int page,
      int pageSize
  ) {
  }
}
