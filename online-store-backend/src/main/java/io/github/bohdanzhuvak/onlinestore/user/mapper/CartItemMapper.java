package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.user.model.CartItem;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "price", source = "product.price")
  CartItemResponse toResponse(CartItem cartItem);
}

