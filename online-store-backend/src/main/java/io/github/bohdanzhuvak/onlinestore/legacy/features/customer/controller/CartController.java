package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.UpdateCartItemRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.CartService;
import io.github.bohdanzhuvak.onlinestore.legacy.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @GetMapping
  public CartResponse getCart(@CurrentUser UserPrincipal user) {
    return cartService.getCart(user.id());
  }

  @PostMapping("/add")
  public CartResponse addToCart(@CurrentUser UserPrincipal user, @Valid @RequestBody AddToCartRequest request) {
    return cartService.addToCart(user.id(), request);
  }

  @PutMapping("/update")
  public CartResponse updateCart(@CurrentUser UserPrincipal user, @Valid @RequestBody UpdateCartItemRequest request) {
    return cartService.updateCart(user.id(), request);
  }

  @DeleteMapping("/clear")
  public void clearCart(@CurrentUser UserPrincipal user) {
    cartService.clearCart(user.id());
  }


}
