package io.github.bohdanzhuvak.onlinestore.delivery.application.admin;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDeliveriesUseCase {
  private final DeliveryRepository deliveryRepository;

  public GetAllDeliveriesUseCase(DeliveryRepository deliveryRepository) {
    this.deliveryRepository = deliveryRepository;
  }

  public List<Delivery> execute(GetAllDeliveriesCommand command) {
    if (command.userId() != null && command.status() != null) {
      // Filter by both user and status - would need additional repository method
      return deliveryRepository.findByUserId(command.userId())
          .stream()
          .filter(delivery -> delivery.getStatus() == command.status())
          .toList();
    } else if (command.userId() != null) {
      return deliveryRepository.findByUserId(command.userId(), command.offset(), command.limit());
    } else if (command.status() != null) {
      return deliveryRepository.findByStatus(command.status(), command.offset(), command.limit());
    } else {
      return deliveryRepository.findAll(command.offset(), command.limit());
    }
  }

  public record GetAllDeliveriesCommand(
      UserId userId,
      DeliveryStatus status,
      int offset,
      int limit
  ) {
  }
}
