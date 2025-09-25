package io.github.bohdanzhuvak.onlinestore.user.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
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

  List<UserEntity> findByRole(UserRole role);

  List<UserEntity> findByActiveTrue();

  List<UserEntity> findByActiveFalse();

  @Query("SELECT u FROM UserEntity u WHERE " +
      "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
      "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<UserEntity> findByNameContaining(@Param("name") String name);

  @Query("SELECT u FROM UserEntity u WHERE u.active = true")
  List<UserEntity> findActiveUsers(Pageable pageable);

  @Query("SELECT u FROM UserEntity u WHERE u.role = :role")
  List<UserEntity> findByRole(@Param("role") UserRole role, Pageable pageable);

  @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.active = true")
  long countActiveUsers();

  @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role = :role")
  long countByRole(@Param("role") UserRole role);

  boolean existsByEmail(String email);
}
