package io.github.bohdanzhuvak.onlinestore.delivery.application.usecase;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.out.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.util.Optional;

public class GetDeliveryUseCase {
  private final DeliveryRepository deliveryRepository;

  public GetDeliveryUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public Optional<Delivery> execute(DeliveryId deliveryId, UserId userId) {
    return deliveryRepository.findById(deliveryId)
        .filter(delivery -> delivery.belongsTo(userId));
  }
}
