package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.delivery.DeliveryAddressResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.common.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.common.model.Order;
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
