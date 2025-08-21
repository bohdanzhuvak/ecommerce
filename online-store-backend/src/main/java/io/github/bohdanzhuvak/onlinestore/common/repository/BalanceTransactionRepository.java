package io.github.bohdanzhuvak.onlinestore.common.repository;

import io.github.bohdanzhuvak.onlinestore.common.model.BalanceTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Long> {
    Page<BalanceTransaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
