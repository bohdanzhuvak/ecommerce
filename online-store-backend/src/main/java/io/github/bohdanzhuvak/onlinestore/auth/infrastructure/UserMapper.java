package io.github.bohdanzhuvak.onlinestore.auth.infrastructure;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Password;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Username;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

  public User toDomain(UserEntity entity) {
    return User.restore(
        UserId.of(entity.getId()),
        Username.of(entity.getUsername()),
        Email.of(entity.getEmail()),
        Password.ofHashed(entity.getPassword()),
        entity.getRole(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.isActive()
    );
  }

  public UserEntity toEntity(User user) {
    return new UserEntity(
        user.getId().getValue(),
        user.getUsername().getValue(),
        user.getEmail().getValue(),
        user.getPassword().getHashedValue(),
        user.getRole(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.isActive()
    );
  }

  public List<User> toDomainList(List<UserEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}
