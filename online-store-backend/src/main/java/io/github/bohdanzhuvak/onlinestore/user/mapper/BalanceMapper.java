package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.common.model.BalanceTransaction;
import io.github.bohdanzhuvak.onlinestore.user.dto.balance.BalanceTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

  @Mapping(target = "orderId", source = "order.id")
  BalanceTransactionResponse toDto(BalanceTransaction transaction);
}
