package io.github.bohdanzhuvak.onlinestore.controller;

import io.github.bohdanzhuvak.onlinestore.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.dto.cart.UpdateCartItemRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@NoArgsConstructor
public class CartController {

  @GetMapping("/")
  public CartResponse getCart() {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/add")
  public void addToCart(@RequestBody AddToCartRequest request) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/update")
  public void updateCart(@RequestBody UpdateCartItemRequest request) {
    throw new UnsupportedOperationException();
  }


}
