package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.impl;

import io.github.bohdanzhuvak.onlinestore.domain.factory.CartFactory;
import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CartRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.UpdateCartItemRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper.CartMapper;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final CartMapper cartMapper;

  @Override
  public CartResponse getCart(Long userId) {
    Cart cart = findOrCreateCart(userId);
    return cartMapper.toResponse(cart);
  }

  @Transactional
  @Override
  public CartResponse addToCart(Long userId, AddToCartRequest request) {
    Cart cart = findOrCreateCart(userId);
    Product product = findProduct(request.getProductId());

    cart.addItem(product, request.getQuantity());
    cartRepository.save(cart);

    return cartMapper.toResponse(cart);
  }

  @Transactional
  @Override
  public CartResponse updateCart(Long userId, UpdateCartItemRequest request) {
    Cart cart = findOrCreateCart(userId);
    Product product = findProduct(request.getProductId());

    cart.updateItem(product, request.getQuantity());
    cartRepository.save(cart);

    return cartMapper.toResponse(cart);
  }

  @Transactional
  @Override
  public void clearCart(Long userId) {
    Cart cart = findOrCreateCart(userId);
    cart.clear();
    cartRepository.save(cart);
  }

  // ------------------- HELPERS -------------------
  private User findUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
  }

  private Cart findOrCreateCart(Long userId) {
    User user = findUser(userId);
    return cartRepository.findByUserId(userId)
        .orElseGet(() -> cartRepository.save(CartFactory.create(user)));
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
  }
}
