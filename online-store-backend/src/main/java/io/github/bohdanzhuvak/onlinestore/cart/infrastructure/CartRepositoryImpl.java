package io.github.bohdanzhuvak.onlinestore.cart.infrastructure;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {
  private final JpaCartRepository jpaCartRepository;
  private final CartMapper cartMapper;

  public CartRepositoryImpl(JpaCartRepository jpaCartRepository, CartMapper cartMapper) {
    this.jpaCartRepository = jpaCartRepository;
    this.cartMapper = cartMapper;
  }

  @Override
  public Cart save(Cart cart) {
    CartEntity entity = cartMapper.toEntity(cart);
    CartEntity savedEntity = jpaCartRepository.save(entity);
    return cartMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Cart> findById(CartId id) {
    return jpaCartRepository.findById(id.getValue())
        .map(cartMapper::toDomain);
  }

  @Override
  public Optional<Cart> findByUserId(UserId userId) {
    return jpaCartRepository.findByUserId(userId.getValue())
        .map(cartMapper::toDomain);
  }

  @Override
  public void delete(CartId id) {
    jpaCartRepository.deleteById(id.getValue());
  }

  @Override
  public boolean existsById(CartId id) {
    return jpaCartRepository.existsById(id.getValue());
  }

  @Override
  public boolean existsByUserId(UserId userId) {
    return jpaCartRepository.existsByUserId(userId.getValue());
  }
}
