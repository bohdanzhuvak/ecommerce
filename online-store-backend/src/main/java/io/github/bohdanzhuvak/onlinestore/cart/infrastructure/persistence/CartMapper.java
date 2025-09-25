package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

  public Cart toDomain(CartEntity entity) {
    List<CartItem> cartItems = entity.getItems().stream()
        .map(this::toDomainCartItem)
        .collect(Collectors.toList());

    return Cart.restore(
        CartId.of(entity.getId()),
        UserId.of(entity.getUserId()),
        cartItems,
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  public CartEntity toEntity(Cart cart) {
    CartEntity entity = new CartEntity();
    entity.setId(cart.getId().getValue());
    entity.setUserId(cart.getUserId().getValue());
    entity.setCreatedAt(cart.getCreatedAt());
    entity.setUpdatedAt(cart.getUpdatedAt());

    List<CartItemEntity> cartItemEntities = cart.getItems().stream()
        .map(item -> toEntityCartItem(item, entity))
        .collect(Collectors.toList());
    entity.setItems(cartItemEntities);

    return entity;
  }

  private CartItem toDomainCartItem(CartItemEntity entity) {
    return new CartItem(
        ProductId.of(entity.getProductId()),
        entity.getProductName(),
        Money.of(entity.getUnitPriceAmount(), entity.getUnitPriceCurrency()),
        entity.getQuantity()
    );
  }

  public List<CartItem> toDomainCartItem(List<CartItemEntity> entity) {
    return entity.stream()
        .map(this::toDomainCartItem).toList();
  }

  private CartItemEntity toEntityCartItem(CartItem cartItem, CartEntity cartEntity) {
    CartItemEntity entity = new CartItemEntity();
    entity.setCart(cartEntity);
    entity.setProductId(cartItem.getProductId().getValue());
    entity.setProductName(cartItem.getProductName());
    entity.setUnitPriceAmount(cartItem.getUnitPrice().getAmount());
    entity.setUnitPriceCurrency(cartItem.getUnitPrice().getCurrency());
    entity.setQuantity(cartItem.getQuantity());
    return entity;
  }
}
