package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
public class AddDeliveryAddressUseCase {
  private final UserRepository userRepository;

  public AddDeliveryAddressUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public DeliveryAddress execute(AddDeliveryAddressCommand addDeliveryAddressCommand) {
    User user = userRepository.findById(addDeliveryAddressCommand.userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    DeliveryAddress deliveryAddress = addDeliveryAddressCommand.address;
    user.addAddress(deliveryAddress);
    if (addDeliveryAddressCommand.isDefault) {
      user.setDefaultAddress(deliveryAddress.id());
    }
    userRepository.save(user);
    return deliveryAddress;
  }

  public record AddDeliveryAddressCommand(UserId userId, DeliveryAddress address, boolean isDefault) {
  }
}
