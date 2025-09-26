package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper;


import io.github.bohdanzhuvak.onlinestore.domain.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "pricePerUnit", source = "product.price")
  OrderItemResponse toResponse(OrderItem orderItem);
}
