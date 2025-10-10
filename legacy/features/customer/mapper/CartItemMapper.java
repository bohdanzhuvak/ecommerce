package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.CartItem;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "price", source = "product.price")
  CartItemResponse toResponse(CartItem cartItem);
}

