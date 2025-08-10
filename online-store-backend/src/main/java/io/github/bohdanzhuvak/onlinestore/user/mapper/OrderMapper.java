package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
  OrderResponse toResponse(Order order);

  List<OrderResponse> toResponse(List<Order> orders);
}
