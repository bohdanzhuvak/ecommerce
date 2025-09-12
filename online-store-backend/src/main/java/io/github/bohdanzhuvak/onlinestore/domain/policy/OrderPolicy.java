package io.github.bohdanzhuvak.onlinestore.domain.policy;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;

public interface OrderPolicy {
  String getType();

  void validate(Order order, Long userId);

  void apply(Order order);
}
