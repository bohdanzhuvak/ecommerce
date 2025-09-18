package io.github.bohdanzhuvak.onlinestore.delivery.application.customer;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDeliveriesUseCase {
  private final DeliveryRepository deliveryRepository;

  public GetDeliveriesUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public List<Delivery> execute(UserId userId) {
    return deliveryRepository.findByUserId(userId);
  }

  public List<Delivery> execute(UserId userId, int offset, int limit) {
    return deliveryRepository.findByUserId(userId, offset, limit);
  }
}
