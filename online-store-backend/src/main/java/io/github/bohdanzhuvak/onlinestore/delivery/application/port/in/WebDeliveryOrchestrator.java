package io.github.bohdanzhuvak.onlinestore.delivery.application.port.in;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface WebDeliveryOrchestrator {
  Delivery createDelivery(OrderId orderId, UserId userId, DeliveryAddress address);

  Optional<Delivery> getDelivery(DeliveryId deliveryId, UserId userId);

  List<Delivery> getDeliveries(UserId userId);

  List<Delivery> getDeliveries(UserId userId, int offset, int limit);

  Optional<Delivery> trackDelivery(TrackingNumber trackingNumber, UserId userId);

  Delivery updateDeliveryStatus(DeliveryId deliveryId, DeliveryStatus newStatus, String notes);

  List<Delivery> getAllDeliveries(UserId userId, DeliveryStatus status, int offset, int limit);


}
