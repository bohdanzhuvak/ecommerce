package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.user.model.Cart;
import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.CartRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.UpdateCartItemRequest;
import io.github.bohdanzhuvak.onlinestore.user.mapper.CartMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final CartMapper cartMapper;
  private final UserRepository userRepository;

  public CartResponse getCart(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> Cart.builder().user(user).items(new ArrayList<>()).build());
    if (cart.getId() == null) {
      cart = cartRepository.save(cart);
    }

    return cartMapper.toResponse(cart);
  }

  @Transactional
  public CartResponse addToCart(Long userId, AddToCartRequest request) {
    User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found with id: " + userId));
    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> Cart.builder().user(user).items(new ArrayList<>()).build());
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new NotFoundException("Product not found"));

    cart.addItem(product, request.getQuantity());
    cartRepository.save(cart);
    return cartMapper.toResponse(cart);
  }

  @Transactional
  public CartResponse updateCart(Long userId, UpdateCartItemRequest request) {
    User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found with id: " + userId));
    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> Cart.builder().user(user).build());
    Product product = productRepository.findById(request.getProductId())
        .orElseThrow(() -> new NotFoundException("Product not found"));

    cart.updateItem(product, request.getQuantity());
    cartRepository.save(cart);
    return cartMapper.toResponse(cart);
  }

  @Transactional
  public void clearCart(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found with id: " + userId));
    Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> Cart.builder().user(user).build());
    cart.getItems().clear();
    cartRepository.save(cart);
  }
}
