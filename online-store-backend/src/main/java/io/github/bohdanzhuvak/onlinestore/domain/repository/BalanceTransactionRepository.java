package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.BalanceTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BalanceTransactionRepository extends BaseRepository<BalanceTransaction, Long> {
    Page<BalanceTransaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
