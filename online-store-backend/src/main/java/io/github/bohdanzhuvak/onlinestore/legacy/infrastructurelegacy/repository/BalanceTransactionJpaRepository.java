package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.BalanceTransaction;
import io.github.bohdanzhuvak.onlinestore.domain.repository.BalanceTransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface BalanceTransactionJpaRepository extends BaseJpaRepository<BalanceTransaction, Long>, BalanceTransactionRepository {
}
