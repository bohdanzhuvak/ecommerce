package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.out.delivery;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.UserPort;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import org.springframework.stereotype.Service;

@Service
public class UserPortAdapter implements UserPort {
  private final ExternalUserOrchestrator externalUserOrchestrator;

  public UserPortAdapter(ExternalUserOrchestrator externalUserOrchestrator) {
    this.externalUserOrchestrator = externalUserOrchestrator;
  }

  @Override
  public DeliveryAddressSnapshot getDeliveryAddressById(UserId userId, DeliveryAddressId addressId) {
    return toDeliveryAddressSnapshot(externalUserOrchestrator.getDeliveryAddressById(toUserId(userId), toUserAddressId(addressId)));
  }

  private DeliveryAddressSnapshot toDeliveryAddressSnapshot(DeliveryAddress deliveryAddressById) {
    return new DeliveryAddressSnapshot(
        DeliveryAddressId.of(deliveryAddressById.id().getValue()),
        deliveryAddressById.country(),
        deliveryAddressById.city(),
        deliveryAddressById.street(),
        deliveryAddressById.postalCode(),
        deliveryAddressById.country(),
        deliveryAddressById.recipientName());
  }

  private io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId toUserAddressId(DeliveryAddressId addressId) {
    return io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId.of(addressId.getValue());
  }

  private io.github.bohdanzhuvak.onlinestore.user.domain.UserId toUserId(UserId userId) {
    return io.github.bohdanzhuvak.onlinestore.user.domain.UserId.of(userId.getValue());
  }
}
