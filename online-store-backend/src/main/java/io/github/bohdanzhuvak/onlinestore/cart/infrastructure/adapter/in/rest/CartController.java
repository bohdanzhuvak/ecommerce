package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.WebCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource.AddItemRequest;
import io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource.UpdateItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/cart")
public class CartController {
  private final WebCartOrchestrator webCartOrchestrator;

  public CartController(WebCartOrchestrator webCartOrchestrator) {
    this.webCartOrchestrator = webCartOrchestrator;
  }

  @GetMapping
  public ResponseEntity<Cart> getCart(@CurrentUserId String userId) {
    UserId userIdObj = UserId.of(userId);
    Optional<Cart> cart = webCartOrchestrator.getCart(userIdObj);
    return cart.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/items")
  public ResponseEntity<Cart> addItem(@CurrentUserId String userId, @RequestBody AddItemRequest request) {
    try {
      Cart cart = webCartOrchestrator.addItemToCart(
          UserId.of(userId),
          ProductId.of(request.productId()),
          request.quantity()
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/items/{productId}")
  public ResponseEntity<Cart> updateItem(@CurrentUserId String userId,
                                         @PathVariable String productId,
                                         @RequestBody UpdateItemRequest request) {
    try {
      Cart cart = webCartOrchestrator.updateCartItem(
          UserId.of(userId),
          ProductId.of(productId),
          request.quantity()
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/items/{productId}")
  public ResponseEntity<Cart> removeItem(@CurrentUserId String userId, @PathVariable String productId) {
    try {
      Cart cart = webCartOrchestrator.removeItemFromCart(
          UserId.of(userId),
          ProductId.of(productId)
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping
  public ResponseEntity<Cart> clearCart(@CurrentUserId String userId) {
    try {
      Cart cart = webCartOrchestrator.clearCart(UserId.of(userId));
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
