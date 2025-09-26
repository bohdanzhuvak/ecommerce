package io.github.bohdanzhuvak.onlinestore.order.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.application.service.WebOrderOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.CancelOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.CreateOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetAllOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.GetOrdersUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.PayOrderUseCase;
import io.github.bohdanzhuvak.onlinestore.order.application.usecase.UpdateOrderStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

  @Bean
  public WebOrderOrchestrator webOrderOrchestrator(CreateOrderUseCase createOrderUseCase,
                                                   PayOrderUseCase payOrderUseCase,
                                                   CancelOrderUseCase cancelOrderUseCase,
                                                   GetOrdersUseCase getOrdersUseCase,
                                                   GetOrderUseCase getOrderUseCase,
                                                   GetAllOrdersUseCase getAllOrdersUseCase,
                                                   UpdateOrderStatusUseCase updateOrderStatusUseCase) {
    return new WebOrderOrchestratorService(createOrderUseCase, getOrdersUseCase, getOrderUseCase, payOrderUseCase, cancelOrderUseCase, getAllOrdersUseCase, updateOrderStatusUseCase);
  }

}
