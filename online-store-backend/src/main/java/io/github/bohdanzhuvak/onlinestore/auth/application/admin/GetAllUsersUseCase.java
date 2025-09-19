package io.github.bohdanzhuvak.onlinestore.auth.application.admin;

import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersUseCase {
  private final UserRepository userRepository;

  public GetAllUsersUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> execute(GetAllUsersCommand command) {
    if (command.role() != null) {
      return userRepository.findByRole(command.role(), command.offset(), command.limit());
    } else {
      return userRepository.findAll(command.offset(), command.limit());
    }
  }

  public record GetAllUsersCommand(
      UserRole role,
      int offset,
      int limit
  ) {
  }
}
