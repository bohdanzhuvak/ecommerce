package io.github.bohdanzhuvak.onlinestore.delivery.application.customer;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
