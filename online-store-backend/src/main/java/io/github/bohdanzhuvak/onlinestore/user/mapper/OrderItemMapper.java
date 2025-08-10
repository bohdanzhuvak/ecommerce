package io.github.bohdanzhuvak.onlinestore.user.mapper;


import io.github.bohdanzhuvak.onlinestore.common.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "pricePerUnit", source = "product.price")
  OrderItemResponse toResponse(OrderItem orderItem);
}
