package io.github.bohdanzhuvak.onlinestore.order.infrastructure.delivery;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.DeliveryPort;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPortAdapter implements DeliveryPort {
  @Override
  public boolean existsAndBelongsToUser(String addressId, String userId) {
    return false;
  }
}
