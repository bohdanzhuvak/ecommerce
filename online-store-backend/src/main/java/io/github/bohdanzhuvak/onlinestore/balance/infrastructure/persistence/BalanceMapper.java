package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Money;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionId;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BalanceMapper {

  public Balance toDomain(BalanceEntity entity) {
    return Balance.restore(
        UserId.of(entity.getUserId()),
        Money.of(entity.getAmount(), entity.getCurrency()),
        entity.getLastUpdated()
    );
  }

  public BalanceEntity toEntity(Balance balance) {
    return new BalanceEntity(
        balance.getUserId().getValue(),
        balance.getAmount().getAmount(),
        balance.getAmount().getCurrency(),
        balance.getLastUpdated()
    );
  }

  public Transaction toDomain(TransactionEntity entity) {
    return Transaction.restore(
        TransactionId.of(entity.getId()),
        UserId.of(entity.getUserId()),
        entity.getType(),
        Money.of(entity.getAmount(), entity.getCurrency()),
        Money.of(entity.getBalanceAfter(), entity.getCurrency()),
        entity.getDescription(),
        entity.getOrderId(),
        entity.getCreatedAt()
    );
  }

  public TransactionEntity toEntity(Transaction transaction) {
    return new TransactionEntity(
        transaction.getId().getValue(),
        transaction.getUserId().getValue(),
        transaction.getType(),
        transaction.getAmount().getAmount(),
        transaction.getAmount().getCurrency(),
        transaction.getBalanceAfter().getAmount(),
        transaction.getDescription(),
        transaction.getOrderId(),
        transaction.getCreatedAt()
    );
  }

  public List<Transaction> toDomainList(List<TransactionEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}
