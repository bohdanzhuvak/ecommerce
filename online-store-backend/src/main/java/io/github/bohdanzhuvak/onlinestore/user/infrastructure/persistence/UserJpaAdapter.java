package io.github.bohdanzhuvak.onlinestore.user.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.user.application.port.out.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaAdapter implements UserRepository {
  private final JpaUserRepository jpaUserRepository;
  private final UserMapper userMapper;

  public UserJpaAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
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
  public List<User> findAll() {
    return userMapper.toDomainList(jpaUserRepository.findAll());
  }

  @Override
  public List<User> findByRole(UserRole role) {
    return userMapper.toDomainList(jpaUserRepository.findByRole(role));
  }

  @Override
  public List<User> findActiveUsers() {
    return userMapper.toDomainList(jpaUserRepository.findByActiveTrue());
  }

  @Override
  public List<User> findInactiveUsers() {
    return userMapper.toDomainList(jpaUserRepository.findByActiveFalse());
  }

  @Override
  public List<User> findByNameContaining(String name) {
    return userMapper.toDomainList(jpaUserRepository.findByNameContaining(name));
  }

  @Override
  public List<User> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return userMapper.toDomainList(jpaUserRepository.findAll(pageable).getContent());
  }

  @Override
  public List<User> findActiveUsers(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return userMapper.toDomainList(jpaUserRepository.findActiveUsers(pageable));
  }

  @Override
  public List<User> findByRole(UserRole role, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return userMapper.toDomainList(jpaUserRepository.findByRole(role, pageable));
  }

  @Override
  public long count() {
    return jpaUserRepository.count();
  }

  @Override
  public long countActiveUsers() {
    return jpaUserRepository.countActiveUsers();
  }

  @Override
  public long countByRole(UserRole role) {
    return jpaUserRepository.countByRole(role);
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
  public void delete(UserId id) {
    jpaUserRepository.deleteById(id.getValue());
  }
}
