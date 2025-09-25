package io.github.bohdanzhuvak.onlinestore.order.infrastructure;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.BalancePort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.CartPort;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.UserPort;
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
  public CancelOrderUseCase cancelOrderUseCase(OrderRepository orderRepository) {
    return new CancelOrderUseCase(orderRepository);
  }

  @Bean
  public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository, CartPort cartPort, UserPort userPort) {
    return new CreateOrderUseCase(orderRepository, cartPort, userPort);
  }

  @Bean
  public GetAllOrdersUseCase getAllOrdersUseCase(OrderRepository orderRepository) {
    return new GetAllOrdersUseCase(orderRepository);
  }

  @Bean
  public GetOrdersUseCase getOrdersUseCase(OrderRepository orderRepository) {
    return new GetOrdersUseCase(orderRepository);
  }

  @Bean
  public GetOrderUseCase getOrderUseCase(OrderRepository orderRepository) {
    return new GetOrderUseCase(orderRepository);
  }

  @Bean
  public PayOrderUseCase payOrderUseCase(OrderRepository orderRepository, BalancePort balancePort) {
    return new PayOrderUseCase(orderRepository, balancePort);
  }

  @Bean
  public UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderRepository orderRepository) {
    return new UpdateOrderStatusUseCase(orderRepository);
  }

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
