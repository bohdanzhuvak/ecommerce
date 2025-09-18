package io.github.bohdanzhuvak.onlinestore.delivery.application.admin;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeliveryStatusUseCase {
  private final DeliveryRepository deliveryRepository;

  public UpdateDeliveryStatusUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public Delivery execute(UpdateDeliveryStatusCommand command) {
    Delivery delivery = deliveryRepository.findById(command.deliveryId())
        .orElseThrow(() -> new IllegalArgumentException("Delivery not found: " + command.deliveryId()));

    // Update status based on the new status
    switch (command.newStatus()) {
      case PICKED_UP -> delivery.pickUp();
      case IN_TRANSIT -> delivery.markInTransit();
      case OUT_FOR_DELIVERY -> delivery.markOutForDelivery();
      case DELIVERED -> delivery.deliver();
      case FAILED -> delivery.markFailed(command.notes());
      case RETURNED -> delivery.markReturned(command.notes());
      default -> throw new IllegalArgumentException("Invalid status transition: " + command.newStatus());
    }

    return deliveryRepository.save(delivery);
  }

  public record UpdateDeliveryStatusCommand(
      DeliveryId deliveryId,
      DeliveryStatus newStatus,
      String notes
  ) {
  }
}
