package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.WebCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource.AddItemRequest;
import io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource.CartResponse;
import io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource.UpdateItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "cart", description = "Shopping cart management for customers")
@RestController
@RequestMapping("/api/v1/customer/cart")
public class CartController {
  private final WebCartOrchestrator webCartOrchestrator;

  public CartController(WebCartOrchestrator webCartOrchestrator) {
    this.webCartOrchestrator = webCartOrchestrator;
  }

  @Operation(operationId = "getCart", summary = "Get shopping cart", description = "Get the current shopping cart for the authenticated user")
  @GetMapping
  public ResponseEntity<CartResponse> getCart(@CurrentUserId String userId) {
    UserId userIdObj = UserId.of(userId);
    Optional<Cart> cart = webCartOrchestrator.getCart(userIdObj);
    return cart.map(c -> ResponseEntity.ok(CartResponse.from(c)))
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(operationId = "addItemToCart", summary = "Add item to cart", description = "Add a product to the shopping cart")
  @PostMapping("/items")
  public ResponseEntity<CartResponse> addItem(@CurrentUserId String userId, @RequestBody AddItemRequest request) {
    try {
      Cart cart = webCartOrchestrator.addItemToCart(
          UserId.of(userId),
          ProductId.of(request.productId()),
          request.quantity()
      );
      return ResponseEntity.ok(CartResponse.from(cart));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "updateCartItem", summary = "Update cart item", description = "Update the quantity of a product in the cart")
  @PutMapping("/items/{productId}")
  public ResponseEntity<CartResponse> updateItem(@CurrentUserId String userId,
                                         @PathVariable String productId,
                                         @RequestBody UpdateItemRequest request) {
    try {
      Cart cart = webCartOrchestrator.updateCartItem(
          UserId.of(userId),
          ProductId.of(productId),
          request.quantity()
      );
      return ResponseEntity.ok(CartResponse.from(cart));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "removeItemFromCart", summary = "Remove item from cart", description = "Remove a product from the shopping cart")
  @DeleteMapping("/items/{productId}")
  public ResponseEntity<CartResponse> removeItem(@CurrentUserId String userId, @PathVariable String productId) {
    try {
      Cart cart = webCartOrchestrator.removeItemFromCart(
          UserId.of(userId),
          ProductId.of(productId)
      );
      return ResponseEntity.ok(CartResponse.from(cart));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "clearCart", summary = "Clear shopping cart", description = "Remove all items from the shopping cart")
  @DeleteMapping
  public ResponseEntity<CartResponse> clearCart(@CurrentUserId String userId) {
    try {
      Cart cart = webCartOrchestrator.clearCart(UserId.of(userId));
      return ResponseEntity.ok(CartResponse.from(cart));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
