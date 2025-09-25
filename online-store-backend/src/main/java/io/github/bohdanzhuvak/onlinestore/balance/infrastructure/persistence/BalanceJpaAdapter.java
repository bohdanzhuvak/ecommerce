package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.balance.application.port.out.BalanceRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.Balance;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BalanceJpaAdapter implements BalanceRepository {
  private final JpaBalanceRepository jpaBalanceRepository;
  private final BalanceMapper balanceMapper;

  public BalanceJpaAdapter(JpaBalanceRepository jpaBalanceRepository, BalanceMapper balanceMapper) {
    this.jpaBalanceRepository = jpaBalanceRepository;
    this.balanceMapper = balanceMapper;
  }

  @Override
  public Balance save(Balance balance) {
    BalanceEntity entity = balanceMapper.toEntity(balance);
    BalanceEntity savedEntity = jpaBalanceRepository.save(entity);
    return balanceMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Balance> findByUserId(UserId userId) {
    return jpaBalanceRepository.findByUserId(userId.getValue())
        .map(balanceMapper::toDomain);
  }

  @Override
  public boolean existsByUserId(UserId userId) {
    return jpaBalanceRepository.existsByUserId(userId.getValue());
  }

  @Override
  public void deleteByUserId(UserId userId) {
    jpaBalanceRepository.deleteByUserId(userId.getValue());
  }
}
