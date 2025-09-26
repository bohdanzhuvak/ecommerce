package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
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
