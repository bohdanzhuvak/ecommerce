package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, String> {

  List<TransactionEntity> findByUserId(String userId);

  @Query("SELECT t FROM TransactionEntity t WHERE t.userId = :userId ORDER BY t.createdAt DESC")
  List<TransactionEntity> findByUserId(@Param("userId") String userId, Pageable pageable);

  List<TransactionEntity> findByType(TransactionType type);

  List<TransactionEntity> findByUserIdAndType(String userId, TransactionType type);

  List<TransactionEntity> findByOrderId(String orderId);

  @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.userId = :userId")
  long countByUserId(@Param("userId") String userId);

  @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.type = :type")
  long countByType(@Param("type") TransactionType type);
}
