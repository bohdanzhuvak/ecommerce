package io.github.bohdanzhuvak.onlinestore.user.application.usecases;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserProfileByIdUseCase {
  private final UserRepository userRepository;

  public GetUserProfileByIdUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(UserId userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
  }
}
