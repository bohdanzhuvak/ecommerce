package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

  public User toDomain(UserEntity entity) {
    return User.restore(
        UserId.of(entity.getId()),
        Email.of(entity.getEmail()),
        Password.ofHashed(entity.getPasswordHash()),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getRole(),
        entity.isActive(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  public UserEntity toEntity(User user) {
    return new UserEntity(
        user.getId().getValue(),
        user.getEmail().getValue(),
        user.getPassword().getHashedValue(),
        user.getFirstName(),
        user.getLastName(),
        user.getRole(),
        user.isActive(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public List<User> toDomainList(List<UserEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}
