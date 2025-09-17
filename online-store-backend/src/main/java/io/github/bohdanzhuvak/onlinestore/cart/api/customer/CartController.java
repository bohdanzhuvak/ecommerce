package io.github.bohdanzhuvak.onlinestore.cart.api.customer;

import io.github.bohdanzhuvak.onlinestore.cart.application.CartApplicationService;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/cart")
public class CartController {
  private final CartApplicationService cartApplicationService;

  public CartController(CartApplicationService cartApplicationService) {
    this.cartApplicationService = cartApplicationService;
  }

  @GetMapping
  public ResponseEntity<Cart> getCart(@RequestParam String userId) {
    UserId userIdObj = UserId.of(userId);
    Optional<Cart> cart = cartApplicationService.getCart(userIdObj);
    return cart.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/items")
  public ResponseEntity<Cart> addItem(@RequestBody AddItemRequest request) {
    try {
      Cart cart = cartApplicationService.addItemToCart(
          UserId.of(request.getUserId()),
          ProductId.of(request.getProductId()),
          request.getQuantity()
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/items/{productId}")
  public ResponseEntity<Cart> updateItem(@PathVariable String productId,
                                         @RequestBody UpdateItemRequest request) {
    try {
      Cart cart = cartApplicationService.updateCartItem(
          UserId.of(request.getUserId()),
          ProductId.of(productId),
          request.getQuantity()
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/items/{productId}")
  public ResponseEntity<Cart> removeItem(@PathVariable String productId,
                                         @RequestParam String userId) {
    try {
      Cart cart = cartApplicationService.removeItemFromCart(
          UserId.of(userId),
          ProductId.of(productId)
      );
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping
  public ResponseEntity<Cart> clearCart(@RequestParam String userId) {
    try {
      Cart cart = cartApplicationService.clearCart(UserId.of(userId));
      return ResponseEntity.ok(cart);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class AddItemRequest {
    private String userId;
    private String productId;
    private Integer quantity;

    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getProductId() {
      return productId;
    }

    public void setProductId(String productId) {
      this.productId = productId;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
  }

  public static class UpdateItemRequest {
    private String userId;
    private Integer quantity;

    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
  }
}
