package io.github.bohdanzhuvak.onlinestore.delivery.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.port.out.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.util.Optional;

@UseCase
public class TrackDeliveryUseCase {
  private final DeliveryRepository deliveryRepository;

  public TrackDeliveryUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public Optional<Delivery> execute(TrackingNumber trackingNumber, UserId userId) {
    return deliveryRepository.findByTrackingNumber(trackingNumber)
        .filter(delivery -> delivery.belongsTo(userId));
  }
}
