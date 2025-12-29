package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

import java.util.List;

@UseCase
public class GetDeliveryAddressListUseCase {
  private final UserRepository userRepository;

  public GetDeliveryAddressListUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<DeliveryAddress> execute(UserId userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    return user.getAddresses();
  }
}
