package io.github.bohdanzhuvak.onlinestore.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.cart.CartResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
  CartResponse toResponse(Cart cart);

  @AfterMapping
  default void setTotalPrice(Cart cart, @MappingTarget CartResponse cartResponse) {
    cartResponse.setTotalPrice(cart.getTotalPrice());
  }

  Cart toCart(CartResponse cartDTO);
}
