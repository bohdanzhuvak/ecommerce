package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.legacy.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;

@UseCase
public class GetUserProfileByEmailUseCase {
  private final UserRepository userRepository;

  public GetUserProfileByEmailUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(Email userEmail) {
    return userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
  }
}
