package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
public class GetDeliveryAddressUseCase {
  private final UserRepository userRepository;

  public GetDeliveryAddressUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public DeliveryAddress execute(GetDeliveryAddressCommand command) {
    User user = userRepository.findById(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + command.userId()));

    return user.getAddresses().stream()
        .filter(addr -> addr.id().equals(command.addressId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Address not found: " + command.addressId()));
  }

  public record GetDeliveryAddressCommand(
      UserId userId,
      DeliveryAddressId addressId
  ) {
  }

}
