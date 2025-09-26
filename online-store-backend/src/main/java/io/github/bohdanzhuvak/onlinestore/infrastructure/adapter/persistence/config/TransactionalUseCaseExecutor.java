package io.github.bohdanzhuvak.onlinestore.infrastructure.adapter.persistence.config;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class TransactionalUseCaseExecutor {
  @Transactional
  <T> T executeInTransaction(Supplier<T> execution) {
    return execution.get();
  }
}
