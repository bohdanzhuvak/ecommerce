package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.DeliveryAddressResponse;

import java.util.List;

public interface DeliveryAddressService {
  List<DeliveryAddressResponse> getUserAddresses(Long userId);

  DeliveryAddressResponse createAddress(Long userId, CreateDeliveryAddressRequest request);

  DeliveryAddressResponse updateAddress(Long userId, Long addressId, CreateDeliveryAddressRequest request);

  void deleteAddress(Long userId, Long addressId);

  DeliveryAddressResponse getDefaultAddress(Long userId);

}
