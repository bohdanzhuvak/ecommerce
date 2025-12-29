package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
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
        toDeliveryAddresses(entity.getAddresses()),
        entity.getDefaultAddressId() != null ? DeliveryAddressId.of(entity.getDefaultAddressId()) : null,
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
        toDeliveryAddressesEmbeddable(user.getAddresses()),
        user.getDefaultAddressId() != null ? user.getDefaultAddressId().getValue() : null,
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public List<User> toDomainList(List<UserEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  private List<DeliveryAddressEmbeddable> toDeliveryAddressesEmbeddable(List<DeliveryAddress> addresses) {
    return addresses.stream()
        .map(this::toDeliveryAddressesEmbeddable)
        .collect(Collectors.toList());
  }

  private DeliveryAddressEmbeddable toDeliveryAddressesEmbeddable(DeliveryAddress address) {
    return new DeliveryAddressEmbeddable(
        address.id().getValue(),
        address.street(),
        address.city(),
        address.state(),
        address.postalCode(),
        address.country(),
        address.recipientName(),
        address.createdAt()
    );
  }

  private List<DeliveryAddress> toDeliveryAddresses(List<DeliveryAddressEmbeddable> addresses) {
    return addresses.stream()
        .map(this::toDeliveryAddress)
        .collect(Collectors.toList());
  }

  private DeliveryAddress toDeliveryAddress(DeliveryAddressEmbeddable embeddable) {
    return new DeliveryAddress(
        DeliveryAddressId.of(embeddable.getAddressId()),
        embeddable.getStreet(),
        embeddable.getCity(),
        embeddable.getState(),
        embeddable.getPostalCode(),
        embeddable.getCountry(),
        embeddable.getRecipientName(),
        embeddable.getCreatedAt()
    );
  }
}
