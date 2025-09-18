package io.github.bohdanzhuvak.onlinestore.delivery.application.customer;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
