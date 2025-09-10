package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
