package io.github.bohdanzhuvak.onlinestore.order.infrastructure;

import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderItem;
import io.github.bohdanzhuvak.onlinestore.order.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

  public Order toDomain(OrderEntity entity) {
    List<OrderItem> orderItems = entity.getItems().stream()
        .map(this::toDomainOrderItem)
        .collect(Collectors.toList());

    return Order.restore(
        OrderId.of(entity.getId()),
        UserId.of(entity.getUserId()),
        orderItems,
        Money.of(entity.getTotalPriceAmount(), entity.getTotalPriceCurrency()),
        entity.getStatus(),
        entity.getDeliveryAddressId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  public OrderEntity toEntity(Order order) {
    OrderEntity entity = new OrderEntity(
        order.getId().getValue(),
        order.getUserId().getValue(),
        order.getTotalPrice().getAmount(),
        order.getTotalPrice().getCurrency(),
        order.getStatus(),
        order.getDeliveryAddressId(),
        order.getCreatedAt(),
        order.getUpdatedAt()
    );

    List<OrderItemEntity> orderItemEntities = order.getItems().stream()
        .map(item -> toEntityOrderItem(item, entity))
        .collect(Collectors.toList());
    entity.setItems(orderItemEntities);

    return entity;
  }

  public List<Order> toDomainList(List<OrderEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  private OrderItem toDomainOrderItem(OrderItemEntity entity) {
    return new OrderItem(
        ProductId.of(entity.getProductId()),
        entity.getProductName(),
        Money.of(entity.getUnitPriceAmount(), entity.getUnitPriceCurrency()),
        entity.getQuantity()
    );
  }

  private OrderItemEntity toEntityOrderItem(OrderItem orderItem, OrderEntity orderEntity) {
    return new OrderItemEntity(
        orderEntity,
        orderItem.productId().getValue(),
        orderItem.productName(),
        orderItem.unitPrice().getAmount(),
        orderItem.unitPrice().getCurrency(),
        orderItem.quantity()
    );
  }
}
