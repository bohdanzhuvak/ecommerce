package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
