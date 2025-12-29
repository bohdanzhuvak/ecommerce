package io.github.bohdanzhuvak.onlinestore.user.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

@UseCase
public class DeleteDeliveryAddressUseCase {
  private final UserRepository userRepository;

  public DeleteDeliveryAddressUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(DeleteDeliveryAddressCommand command) {
    User user = userRepository.findById(command.userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    user.getAddresses().removeIf(deliveryAddress -> deliveryAddress.id().equals(command.deliveryAddressId));

    userRepository.save(user);
  }

  public record DeleteDeliveryAddressCommand(UserId userId, DeliveryAddressId deliveryAddressId) {
  }
}
