package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.order.AdminOrderRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.order.AdminOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AdminOrderItemMapper.class})
public interface AdminOrderMapper extends AdminBaseMapper<Order, AdminOrderResponse, AdminOrderRequest> {

  @Override
  @Mapping(target = "id", source = "id")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "totalPrice", source = "totalPrice")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "items", source = "items")
  @Mapping(target = "deliveryAddress", source = "deliveryAddress")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  @Mapping(target = "metadata", ignore = true)
  @Mapping(target = "editable", ignore = true)
  @Mapping(target = "deletable", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  AdminOrderResponse toResponseDto(Order entity);

  @Override
  @Mapping(target = "totalPrice", source = "totalPrice")
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "status", source = "status")
  @Mapping(target = "items", ignore = true)
  @Mapping(target = "deliveryAddress", ignore = true)
  Order toEntity(AdminOrderRequest dto);

  @Override
  @Mapping(target = "totalPrice", source = "totalPrice")
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "status", source = "status")
  @Mapping(target = "items", ignore = true)
  @Mapping(target = "deliveryAddress", ignore = true)
  void updateEntity(@MappingTarget Order entity, AdminOrderRequest dto);
}
