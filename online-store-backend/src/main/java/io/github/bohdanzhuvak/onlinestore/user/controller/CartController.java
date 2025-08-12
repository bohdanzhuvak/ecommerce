package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.common.auth.security.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.UpdateCartItemRequest;
import io.github.bohdanzhuvak.onlinestore.user.service.CartService;
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
    return cartService.getCart(user.getId());
  }

  @PostMapping("/add")
  public CartResponse addToCart(@CurrentUser UserPrincipal user, @Valid @RequestBody AddToCartRequest request) {
    return cartService.addToCart(user.getId(), request);
  }

  @PutMapping("/update")
  public CartResponse updateCart(@CurrentUser UserPrincipal user, @Valid @RequestBody UpdateCartItemRequest request) {
    return cartService.updateCart(user.getId(), request);
  }

  @DeleteMapping("/clear")
  public void clearCart(@CurrentUser UserPrincipal user) {
    cartService.clearCart(user.getId());
  }


}
