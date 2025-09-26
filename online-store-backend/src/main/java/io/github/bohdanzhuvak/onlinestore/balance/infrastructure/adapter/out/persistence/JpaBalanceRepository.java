package io.github.bohdanzhuvak.onlinestore.balance.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBalanceRepository extends JpaRepository<BalanceEntity, String> {

  Optional<BalanceEntity> findByUserId(String userId);

  boolean existsByUserId(String userId);

  void deleteByUserId(String userId);
}
