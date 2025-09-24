package io.github.bohdanzhuvak.onlinestore.user.application.usecases;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserProfileByEmailUseCase {
  private final UserRepository userRepository;

  public GetUserProfileByEmailUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(Email userEmail) {
    return userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
  }
}
