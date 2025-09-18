package io.github.bohdanzhuvak.onlinestore.delivery.application;

import io.github.bohdanzhuvak.onlinestore.delivery.application.admin.GetAllDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.admin.UpdateDeliveryStatusUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.customer.CreateDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.customer.GetDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.customer.GetDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.customer.TrackDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryApplicationService {
  private final CreateDeliveryUseCase createDeliveryUseCase;
  private final GetDeliveryUseCase getDeliveryUseCase;
  private final GetDeliveriesUseCase getDeliveriesUseCase;
  private final TrackDeliveryUseCase trackDeliveryUseCase;
  private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
  private final GetAllDeliveriesUseCase getAllDeliveriesUseCase;

  public DeliveryApplicationService(CreateDeliveryUseCase createDeliveryUseCase,
                                    GetDeliveryUseCase getDeliveryUseCase,
                                    GetDeliveriesUseCase getDeliveriesUseCase,
                                    TrackDeliveryUseCase trackDeliveryUseCase,
                                    UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase,
                                    GetAllDeliveriesUseCase getAllDeliveriesUseCase) {
    this.createDeliveryUseCase = createDeliveryUseCase;
    this.getDeliveryUseCase = getDeliveryUseCase;
    this.getDeliveriesUseCase = getDeliveriesUseCase;
    this.trackDeliveryUseCase = trackDeliveryUseCase;
    this.updateDeliveryStatusUseCase = updateDeliveryStatusUseCase;
    this.getAllDeliveriesUseCase = getAllDeliveriesUseCase;
  }

  // Customer operations
  public Delivery createDelivery(OrderId orderId, UserId userId, DeliveryAddress address) {
    CreateDeliveryUseCase.CreateDeliveryCommand command = new CreateDeliveryUseCase.CreateDeliveryCommand(
        orderId, userId, address);
    return createDeliveryUseCase.execute(command);
  }

  public Optional<Delivery> getDelivery(DeliveryId deliveryId, UserId userId) {
    return getDeliveryUseCase.execute(deliveryId, userId);
  }

  public List<Delivery> getDeliveries(UserId userId) {
    return getDeliveriesUseCase.execute(userId);
  }

  public List<Delivery> getDeliveries(UserId userId, int offset, int limit) {
    return getDeliveriesUseCase.execute(userId, offset, limit);
  }

  public Optional<Delivery> trackDelivery(TrackingNumber trackingNumber, UserId userId) {
    return trackDeliveryUseCase.execute(trackingNumber, userId);
  }

  // Admin operations
  public Delivery updateDeliveryStatus(DeliveryId deliveryId, DeliveryStatus newStatus, String notes) {
    UpdateDeliveryStatusUseCase.UpdateDeliveryStatusCommand command = new UpdateDeliveryStatusUseCase.UpdateDeliveryStatusCommand(
        deliveryId, newStatus, notes);
    return updateDeliveryStatusUseCase.execute(command);
  }

  public List<Delivery> getAllDeliveries(UserId userId, DeliveryStatus status, int offset, int limit) {
    GetAllDeliveriesUseCase.GetAllDeliveriesCommand command = new GetAllDeliveriesUseCase.GetAllDeliveriesCommand(
        userId, status, offset, limit);
    return getAllDeliveriesUseCase.execute(command);
  }
}
