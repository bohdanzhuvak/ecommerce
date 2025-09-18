package io.github.bohdanzhuvak.onlinestore.user.application.customer;

import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserProfileUseCase {
  private final UserRepository userRepository;

  public GetUserProfileUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> execute(UserId userId) {
    return userRepository.findById(userId);
  }
}
