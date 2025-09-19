package io.github.bohdanzhuvak.onlinestore.auth.infrastructure;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRepository;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final JpaUserRepository jpaUserRepository;
  private final UserMapper userMapper;

  public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
    this.jpaUserRepository = jpaUserRepository;
    this.userMapper = userMapper;
  }

  @Override
  public User save(User user) {
    UserEntity entity = userMapper.toEntity(user);
    UserEntity savedEntity = jpaUserRepository.save(entity);
    return userMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<User> findById(UserId id) {
    return jpaUserRepository.findById(id.getValue())
        .map(userMapper::toDomain);
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    return jpaUserRepository.findByEmail(email.getValue())
        .map(userMapper::toDomain);
  }

  @Override
  public Optional<User> findByUsername(Username username) {
    return jpaUserRepository.findByUsername(username.getValue())
        .map(userMapper::toDomain);
  }

  @Override
  public List<User> findByRole(UserRole role) {
    return userMapper.toDomainList(jpaUserRepository.findByRole(role));
  }

  @Override
  public List<User> findByRole(UserRole role, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return userMapper.toDomainList(jpaUserRepository.findByRole(role, pageable));
  }

  @Override
  public List<User> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return userMapper.toDomainList(jpaUserRepository.findAll(pageable).getContent());
  }

  @Override
  public long countByRole(UserRole role) {
    return jpaUserRepository.countByRole(role);
  }

  @Override
  public long count() {
    return jpaUserRepository.count();
  }

  @Override
  public boolean existsById(UserId id) {
    return jpaUserRepository.existsById(id.getValue());
  }

  @Override
  public boolean existsByEmail(Email email) {
    return jpaUserRepository.existsByEmail(email.getValue());
  }

  @Override
  public boolean existsByUsername(Username username) {
    return jpaUserRepository.existsByUsername(username.getValue());
  }

  @Override
  public void delete(UserId id) {
    jpaUserRepository.deleteById(id.getValue());
  }
}
