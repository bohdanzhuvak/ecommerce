package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
public class UpdateDeliveryAddressUseCase {
  private final UserRepository userRepository;

  public UpdateDeliveryAddressUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public DeliveryAddress execute(UpdateDeliveryAddressCommand command) {
    User user = userRepository.findById(command.userId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    user.updateAddress(command.address);
    if (command.isDefault()) {
      user.setDefaultAddress(command.address.id());
    }
    userRepository.save(user);
    return command.address;
  }

  public record UpdateDeliveryAddressCommand(UserId userId, DeliveryAddress address, boolean isDefault) {
  }
}
