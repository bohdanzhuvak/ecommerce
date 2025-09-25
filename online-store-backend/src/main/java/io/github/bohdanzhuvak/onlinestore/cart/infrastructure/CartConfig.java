package io.github.bohdanzhuvak.onlinestore.cart.infrastructure;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.ExternalCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.WebCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.ProductPort;
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
  public AddItemToCartUseCase addItemToCartUseCase(CartRepository cartRepository, ProductPort productPort) {
    return new AddItemToCartUseCase(cartRepository, productPort);
  }

  @Bean
  public ClearCartUseCase clearCartUseCase(CartRepository cartRepository) {
    return new ClearCartUseCase(cartRepository);
  }

  @Bean
  public GetCartItemsUseCase getCartItemsUseCase(CartRepository cartRepository) {
    return new GetCartItemsUseCase(cartRepository);
  }

  @Bean
  public GetCartUseCase getCartUseCase(CartRepository cartRepository) {
    return new GetCartUseCase(cartRepository);
  }

  @Bean
  public RemoveItemFromCartUseCase removeItemFromCartUseCase(CartRepository cartRepository) {
    return new RemoveItemFromCartUseCase(cartRepository);
  }

  @Bean
  public UpdateCartItemUseCase updateCartItemUseCase(CartRepository cartRepository) {
    return new UpdateCartItemUseCase(cartRepository);
  }

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
