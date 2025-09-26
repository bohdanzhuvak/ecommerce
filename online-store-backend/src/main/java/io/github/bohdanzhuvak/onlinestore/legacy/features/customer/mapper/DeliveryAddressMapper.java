package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.DeliveryAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {
  DeliveryAddressResponse toResponse(DeliveryAddress address);

  @Mapping(target = "user", ignore = true)
  @Mapping(target = "originalId", ignore = true)
  @Mapping(target = "isTechnical", ignore = true)
  DeliveryAddress toEntity(CreateDeliveryAddressRequest request);

  default DeliveryAddress updateEntity(DeliveryAddress address, CreateDeliveryAddressRequest request) {
    address.setStreet(request.getStreet());
    address.setCity(request.getCity());
    address.setPostalCode(request.getPostalCode());
    address.setCountry(request.getCountry());
    address.setPhone(request.getPhone());
    address.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));
    return address;
  }
}
