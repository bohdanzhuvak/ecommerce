package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.user.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.UpdateCartItemRequest;
import io.github.bohdanzhuvak.onlinestore.user.service.CartService;
import lombok.NoArgsConstructor;
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
  private final Long mockUserId = 0L; // Mock user ID for demonstration purposes

  @GetMapping("/")
  public CartResponse getCart() {
    return cartService.getCart(mockUserId);
  }

  @PostMapping("/add")
  public CartResponse addToCart(@RequestBody AddToCartRequest request) {
    return cartService.addToCart(mockUserId, request);
  }

  @PutMapping("/update")
  public CartResponse updateCart(@RequestBody UpdateCartItemRequest request) {
    return cartService.updateCart(mockUserId, request);
  }

  @DeleteMapping("/clear")
  public void clearCart() {
    cartService.clearCart(mockUserId);
  }


}
