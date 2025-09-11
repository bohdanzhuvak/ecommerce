package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.OrderItem;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderItemRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminOrderItemMapper extends AdminBaseMapper<OrderItem, AdminOrderItemResponse, AdminOrderItemRequest> {

  @Override
  @Mapping(target = "id", source = "id")
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "pricePerUnit", source = "pricePerUnit")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "metadata", ignore = true)
  @Mapping(target = "editable", ignore = true)
  @Mapping(target = "deletable", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  AdminOrderItemResponse toResponseDto(OrderItem entity);

  @Override
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "pricePerUnit", source = "pricePerUnit")
  @Mapping(target = "product", ignore = true)
  @Mapping(target = "order", ignore = true)
  OrderItem toEntity(AdminOrderItemRequest dto);

  @Override
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "pricePerUnit", source = "pricePerUnit")
  @Mapping(target = "product", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget OrderItem entity, AdminOrderItemRequest dto);
}
