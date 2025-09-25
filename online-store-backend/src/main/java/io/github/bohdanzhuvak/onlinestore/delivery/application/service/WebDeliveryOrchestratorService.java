package io.github.bohdanzhuvak.onlinestore.delivery.application.service;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.CreateDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetAllDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.TrackDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.UpdateDeliveryStatusUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.util.List;
import java.util.Optional;

public class WebDeliveryOrchestratorService implements WebDeliveryOrchestrator {
  private final CreateDeliveryUseCase createDeliveryUseCase;
  private final GetDeliveryUseCase getDeliveryUseCase;
  private final GetDeliveriesUseCase getDeliveriesUseCase;
  private final TrackDeliveryUseCase trackDeliveryUseCase;
  private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
  private final GetAllDeliveriesUseCase getAllDeliveriesUseCase;

  public WebDeliveryOrchestratorService(CreateDeliveryUseCase createDeliveryUseCase,
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
  @Override
  public Delivery createDelivery(OrderId orderId, UserId userId, DeliveryAddress address) {
    CreateDeliveryUseCase.CreateDeliveryCommand command = new CreateDeliveryUseCase.CreateDeliveryCommand(
        orderId, userId, address);
    return createDeliveryUseCase.execute(command);
  }

  @Override
  public Optional<Delivery> getDelivery(DeliveryId deliveryId, UserId userId) {
    return getDeliveryUseCase.execute(deliveryId, userId);
  }

  @Override
  public List<Delivery> getDeliveries(UserId userId) {
    return getDeliveriesUseCase.execute(userId);
  }

  @Override
  public List<Delivery> getDeliveries(UserId userId, int offset, int limit) {
    return getDeliveriesUseCase.execute(userId, offset, limit);
  }

  @Override
  public Optional<Delivery> trackDelivery(TrackingNumber trackingNumber, UserId userId) {
    return trackDeliveryUseCase.execute(trackingNumber, userId);
  }

  // Admin operations
  @Override
  public Delivery updateDeliveryStatus(DeliveryId deliveryId, DeliveryStatus newStatus, String notes) {
    UpdateDeliveryStatusUseCase.UpdateDeliveryStatusCommand command = new UpdateDeliveryStatusUseCase.UpdateDeliveryStatusCommand(
        deliveryId, newStatus, notes);
    return updateDeliveryStatusUseCase.execute(command);
  }

  @Override
  public List<Delivery> getAllDeliveries(UserId userId, DeliveryStatus status, int offset, int limit) {
    GetAllDeliveriesUseCase.GetAllDeliveriesCommand command = new GetAllDeliveriesUseCase.GetAllDeliveriesCommand(
        userId, status, offset, limit);
    return getAllDeliveriesUseCase.execute(command);
  }
}
