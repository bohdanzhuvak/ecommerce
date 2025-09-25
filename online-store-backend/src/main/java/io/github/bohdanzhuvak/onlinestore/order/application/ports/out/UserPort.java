package io.github.bohdanzhuvak.onlinestore.order.application.ports.out;

import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

public interface UserPort {

  DeliveryAddressSnapshot getDeliveryAddressById(UserId userId, DeliveryAddressId addressId);
}
