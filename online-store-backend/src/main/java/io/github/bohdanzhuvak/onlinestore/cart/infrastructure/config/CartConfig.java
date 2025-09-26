package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.ExternalCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.WebCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.service.ExternalCartOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.cart.application.service.WebCartOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.AddItemToCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.ClearCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.GetCartItemsUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.GetCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.RemoveItemFromCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.UpdateCartItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

  @Bean
  public WebCartOrchestrator webCartOrchestrator(
      GetCartUseCase getCartUseCase,
      AddItemToCartUseCase addItemToCartUseCase,
      UpdateCartItemUseCase updateCartItemUseCase,
      RemoveItemFromCartUseCase removeItemFromCartUseCase,
      ClearCartUseCase clearCartUseCase
  ) {
    return new WebCartOrchestratorService(getCartUseCase, addItemToCartUseCase, updateCartItemUseCase,
        removeItemFromCartUseCase, clearCartUseCase);
  }

  @Bean
  public ExternalCartOrchestrator externalCartOrchestrator(GetCartItemsUseCase getCartItemsUseCase,
                                                           ClearCartUseCase clearCartUseCase) {
    return new ExternalCartOrchestratorService(getCartItemsUseCase, clearCartUseCase);
  }

}
