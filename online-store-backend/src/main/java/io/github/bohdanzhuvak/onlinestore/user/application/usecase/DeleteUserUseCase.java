package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
public class DeleteUserUseCase {
  private final UserRepository userRepository;

  public DeleteUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(UserId userId) {
    userRepository.delete(userId);
  }
}
