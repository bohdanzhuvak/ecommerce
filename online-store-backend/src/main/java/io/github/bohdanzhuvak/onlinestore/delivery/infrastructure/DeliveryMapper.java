package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryMapper {

  public Delivery toDomain(DeliveryEntity entity) {
    DeliveryAddress address = new DeliveryAddress(
        entity.getStreet(),
        entity.getCity(),
        entity.getState(),
        entity.getPostalCode(),
        entity.getCountry(),
        entity.getRecipientName(),
        entity.getPhoneNumber()
    );

    return Delivery.restore(
        DeliveryId.of(entity.getId()),
        OrderId.of(entity.getOrderId()),
        UserId.of(entity.getUserId()),
        address,
        TrackingNumber.of(entity.getTrackingNumber()),
        entity.getStatus(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getNotes()
    );
  }

  public DeliveryEntity toEntity(Delivery delivery) {
    return new DeliveryEntity(
        delivery.getId().getValue(),
        delivery.getOrderId().getValue(),
        delivery.getUserId().getValue(),
        delivery.getAddress().street(),
        delivery.getAddress().city(),
        delivery.getAddress().state(),
        delivery.getAddress().postalCode(),
        delivery.getAddress().country(),
        delivery.getAddress().recipientName(),
        delivery.getAddress().phoneNumber(),
        delivery.getTrackingNumber().getValue(),
        delivery.getStatus(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt(),
        delivery.getNotes()
    );
  }

  public List<Delivery> toDomainList(List<DeliveryEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}
