package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.delivery.DeliveryAddressResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {AdminOrderItemMapper.class})
public interface AdminOrderMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "deliveryAddress", source = "deliveryAddress")
  OrderResponse toResponse(Order order);

  @Mapping(target = "userId", source = "user.id")
  DeliveryAddressResponse toAdminDeliveryAddressResponse(DeliveryAddress deliveryAddress);

  default Page<OrderResponse> toResponsePage(Page<Order> orders) {
    return orders.map(this::toResponse);
  }
}
