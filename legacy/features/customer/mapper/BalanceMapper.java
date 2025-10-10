package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.BalanceTransaction;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.balance.BalanceTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

  @Mapping(target = "orderId", source = "order.id")
  BalanceTransactionResponse toResponse(BalanceTransaction transaction);
}
