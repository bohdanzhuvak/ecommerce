package io.github.bohdanzhuvak.onlinestore.delivery.application.customer;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class CreateDeliveryUseCase {
  private final DeliveryRepository deliveryRepository;

  public CreateDeliveryUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public Delivery execute(CreateDeliveryCommand command) {
    // Check if delivery already exists for this order
    if (deliveryRepository.existsByOrderId(command.orderId())) {
      throw new IllegalArgumentException("Delivery already exists for order: " + command.orderId());
    }

    // Create delivery
    Delivery delivery = Delivery.create(
        command.orderId(),
        command.userId(),
        command.address()
    );

    // Save delivery
    return deliveryRepository.save(delivery);
  }

  public record CreateDeliveryCommand(
      OrderId orderId,
      UserId userId,
      DeliveryAddress address
  ) {
  }
}
