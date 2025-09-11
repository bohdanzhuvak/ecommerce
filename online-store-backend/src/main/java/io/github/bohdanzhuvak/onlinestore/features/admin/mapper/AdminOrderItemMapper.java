package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderItemRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminOrderItemMapper extends AdminBaseMapper<Order, AdminOrderItemResponse, AdminOrderItemRequest> {
}
