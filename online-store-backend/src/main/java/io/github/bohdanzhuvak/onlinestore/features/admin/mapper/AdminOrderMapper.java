package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminOrderMapper extends AdminBaseMapper<Order, AdminOrderResponse, AdminOrderRequest> {
}
