package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.DeliveryAddressRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.DeliveryAddressResponse;
import io.github.bohdanzhuvak.onlinestore.user.mapper.DeliveryAddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddressService {

  private final DeliveryAddressRepository deliveryAddressRepository;
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final DeliveryAddressMapper deliveryAddressMapper;

  @Transactional(readOnly = true)
  public List<DeliveryAddressResponse> getUserAddresses(Long userId) {
    return deliveryAddressRepository
        .findByUserIdAndIsTechnicalFalseOrderByIsDefaultDescCreatedAtDesc(userId)
        .stream()
        .map(deliveryAddressMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public DeliveryAddressResponse createAddress(Long userId, CreateDeliveryAddressRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    if (request.getIsDefault() != null && request.getIsDefault()) {
      deliveryAddressRepository.clearDefaultAddress(userId);
    }

    DeliveryAddress address = deliveryAddressMapper.toEntity(request);
    address.setUser(user);
    address.setIsTechnical(false);

    DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
    log.info("Created delivery address for user {}", userId);

    return deliveryAddressMapper.toResponse(savedAddress);
  }

  @Transactional
  public DeliveryAddressResponse updateAddress(Long userId, Long addressId, CreateDeliveryAddressRequest request) {
    DeliveryAddress address = deliveryAddressRepository.findById(addressId)
        .orElseThrow(() -> new NotFoundException("Address not found"));

    checkUserAccess(userId, address);

    boolean hasOrders = orderRepository.existsByDeliveryAddressId(addressId);

    if (hasOrders) {
      log.info("Address {} has orders, creating clone for user {}", addressId, userId);

      if (request.getIsDefault() != null && request.getIsDefault()) {
        deliveryAddressRepository.clearDefaultAddress(userId);
      }

      deliveryAddressRepository.markAsTechnical(addressId);

      DeliveryAddress newAddress = deliveryAddressMapper.toEntity(request);
      newAddress.setUser(address.getUser());
      newAddress.setIsTechnical(false);
      newAddress.setOriginalId(addressId);

      DeliveryAddress savedAddress = deliveryAddressRepository.save(newAddress);
      log.info("Created clone address {} for user {} (original: {})", savedAddress.getId(), userId, addressId);

      return deliveryAddressMapper.toResponse(savedAddress);
    } else {
      if (request.getIsDefault() != null && request.getIsDefault()) {
        deliveryAddressRepository.clearDefaultAddress(userId);
      }

      address = deliveryAddressMapper.updateEntity(address, request);

      DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
      log.info("Updated delivery address {} for user {}", addressId, userId);

      return deliveryAddressMapper.toResponse(savedAddress);
    }
  }

  @Transactional
  public void deleteAddress(Long userId, Long addressId) {
    DeliveryAddress address = deliveryAddressRepository.findById(addressId)
        .orElseThrow(() -> new NotFoundException("Address not found"));

    checkUserAccess(userId, address);

    boolean hasOrders = orderRepository.existsByDeliveryAddressId(addressId);

    if (hasOrders) {
      log.info("Address {} has orders, marking as technical and inactive for user {}", addressId, userId);
      deliveryAddressRepository.markAsTechnical(addressId);
    } else {
      deliveryAddressRepository.delete(address);
      log.info("Deleted delivery address {} for user {}", addressId, userId);
    }
  }

  @Transactional(readOnly = true)
  public DeliveryAddressResponse getDefaultAddress(Long userId) {
    return deliveryAddressRepository
        .findByUserIdAndIsDefaultTrueAndIsTechnicalFalse(userId)
        .map(deliveryAddressMapper::toResponse)
        .orElse(null);
  }

  private void checkUserAccess(Long userId, DeliveryAddress address) {
    if (!address.getUser().getId().equals(userId)) {
      throw new AccessDeniedException("User: " + userId + " has no access to address " + address.getId());
    }
  }

}
