package io.github.bohdanzhuvak.onlinestore.balance.infrastructure;

import io.github.bohdanzhuvak.onlinestore.balance.domain.Transaction;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionId;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionRepository;
import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import io.github.bohdanzhuvak.onlinestore.balance.domain.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
  private final JpaTransactionRepository jpaTransactionRepository;
  private final BalanceMapper balanceMapper;

  public TransactionRepositoryImpl(JpaTransactionRepository jpaTransactionRepository, BalanceMapper balanceMapper) {
    this.jpaTransactionRepository = jpaTransactionRepository;
    this.balanceMapper = balanceMapper;
  }

  @Override
  public Transaction save(Transaction transaction) {
    TransactionEntity entity = balanceMapper.toEntity(transaction);
    TransactionEntity savedEntity = jpaTransactionRepository.save(entity);
    return balanceMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Transaction> findById(TransactionId id) {
    return jpaTransactionRepository.findById(id.getValue())
        .map(balanceMapper::toDomain);
  }

  @Override
  public List<Transaction> findByUserId(UserId userId) {
    return balanceMapper.toDomainList(jpaTransactionRepository.findByUserId(userId.getValue()));
  }

  @Override
  public List<Transaction> findByUserId(UserId userId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return balanceMapper.toDomainList(jpaTransactionRepository.findByUserId(userId.getValue(), pageable));
  }

  @Override
  public List<Transaction> findByType(TransactionType type) {
    return balanceMapper.toDomainList(jpaTransactionRepository.findByType(type));
  }

  @Override
  public List<Transaction> findByUserIdAndType(UserId userId, TransactionType type) {
    return balanceMapper.toDomainList(jpaTransactionRepository.findByUserIdAndType(userId.getValue(), type));
  }

  @Override
  public List<Transaction> findByOrderId(String orderId) {
    return balanceMapper.toDomainList(jpaTransactionRepository.findByOrderId(orderId));
  }

  @Override
  public long countByUserId(UserId userId) {
    return jpaTransactionRepository.countByUserId(userId.getValue());
  }

  @Override
  public long countByType(TransactionType type) {
    return jpaTransactionRepository.countByType(type);
  }

  @Override
  public boolean existsById(TransactionId id) {
    return jpaTransactionRepository.existsById(id.getValue());
  }

  @Override
  public void delete(TransactionId id) {
    jpaTransactionRepository.deleteById(id.getValue());
  }
}
