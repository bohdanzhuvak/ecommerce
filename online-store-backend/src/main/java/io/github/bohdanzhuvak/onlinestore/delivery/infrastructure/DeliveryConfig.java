package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.application.port.out.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.application.service.WebDeliveryOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.CreateDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetAllDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetDeliveriesUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.GetDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.TrackDeliveryUseCase;
import io.github.bohdanzhuvak.onlinestore.delivery.application.usecase.UpdateDeliveryStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeliveryConfig {
  @Bean
  public CreateDeliveryUseCase createDeliveryUseCase(DeliveryRepository deliveryRepository) {
    return new CreateDeliveryUseCase(deliveryRepository);
  }

  @Bean
  public GetDeliveryUseCase getDeliveryUseCase(DeliveryRepository deliveryRepository) {
    return new GetDeliveryUseCase(deliveryRepository);
  }

  @Bean
  public GetDeliveriesUseCase getDeliveriesUseCase(DeliveryRepository deliveryRepository) {
    return new GetDeliveriesUseCase(deliveryRepository);
  }

  @Bean
  public TrackDeliveryUseCase trackDeliveryUseCase(DeliveryRepository deliveryRepository) {
    return new TrackDeliveryUseCase(deliveryRepository);
  }

  @Bean
  public UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase(DeliveryRepository deliveryRepository) {
    return new UpdateDeliveryStatusUseCase(deliveryRepository);
  }

  @Bean
  public GetAllDeliveriesUseCase getAllDeliveriesUseCase(DeliveryRepository deliveryRepository) {
    return new GetAllDeliveriesUseCase(deliveryRepository);
  }

  @Bean
  public WebDeliveryOrchestrator webDeliveryOrchestrator(
      CreateDeliveryUseCase createDeliveryUseCase,
      GetDeliveryUseCase getDeliveryUseCase,
      GetDeliveriesUseCase getDeliveriesUseCase,
      TrackDeliveryUseCase trackDeliveryUseCase,
      UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase,
      GetAllDeliveriesUseCase getAllDeliveriesUseCase
  ) {
    return new WebDeliveryOrchestratorService(
        createDeliveryUseCase,
        getDeliveryUseCase,
        getDeliveriesUseCase,
        trackDeliveryUseCase,
        updateDeliveryStatusUseCase,
        getAllDeliveriesUseCase
    );
  }


}
