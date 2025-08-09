package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.common.model.Cart;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
  CartResponse toResponse(Cart cart);

  Cart toCart(CartResponse cartDTO);
}
