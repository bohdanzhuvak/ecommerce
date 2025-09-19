package io.github.bohdanzhuvak.onlinestore.auth.infrastructure;

import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByUsername(String username);

  List<UserEntity> findByRole(UserRole role);

  @Query("SELECT u FROM UserEntity u WHERE u.role = :role ORDER BY u.createdAt DESC")
  List<UserEntity> findByRole(@Param("role") UserRole role, Pageable pageable);

  @Query("SELECT u FROM UserEntity u ORDER BY u.createdAt DESC")
  List<UserEntity> findAll(Pageable pageable);

  @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role = :role")
  long countByRole(@Param("role") UserRole role);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
