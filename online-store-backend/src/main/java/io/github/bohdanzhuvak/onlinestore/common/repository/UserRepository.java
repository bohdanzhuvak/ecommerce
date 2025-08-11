package io.github.bohdanzhuvak.onlinestore.common.repository;

import io.github.bohdanzhuvak.onlinestore.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
