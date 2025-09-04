package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.DeliveryAddressRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.DeliveryAddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional(readOnly = true)
    public List<DeliveryAddressDto> getUserAddresses(Long userId) {
        List<DeliveryAddress> addresses = deliveryAddressRepository
                .findByUserIdAndIsTechnicalFalseOrderByIsDefaultDescCreatedAtDesc(userId);

        return addresses.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryAddressDto createAddress(Long userId, CreateDeliveryAddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Если это первый адрес или помечен как основной, сбросить другие основные адреса
        if (request.getIsDefault() != null && request.getIsDefault()) {
            deliveryAddressRepository.clearDefaultAddress(userId);
        }

        DeliveryAddress address = DeliveryAddress.builder()
                .user(user)
                .street(request.getStreet())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .phone(request.getPhone())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : false)
                .isTechnical(false)
                .originalId(null)
                .build();

        DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
        log.info("Created delivery address for user {}", userId);

        return mapToDto(savedAddress);
    }

    @Transactional
    public DeliveryAddressDto updateAddress(Long userId, Long addressId, CreateDeliveryAddressRequest request) {
        DeliveryAddress address = deliveryAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        // Проверяем, есть ли заказы с этим адресом
        boolean hasOrders = orderRepository.existsByDeliveryAddressId(addressId);

        if (hasOrders) {
            // Если есть заказы - клонируем адрес
            log.info("Address {} has orders, creating clone for user {}", addressId, userId);

            // Помечаем текущий адрес как технический
            deliveryAddressRepository.markAsTechnical(addressId);

            // Создаем новый активный адрес с обновленными данными
            DeliveryAddress newAddress = DeliveryAddress.builder()
                    .user(address.getUser())
                    .street(request.getStreet())
                    .city(request.getCity())
                    .postalCode(request.getPostalCode())
                    .country(request.getCountry())
                    .phone(request.getPhone())
                    .isDefault(request.getIsDefault() != null ? request.getIsDefault() : false)
                    .isTechnical(false)
                    .originalId(addressId) // Связываем с оригинальным адресом
                    .build();

            // Если новый адрес помечен как основной, сбросить другие основные адреса
            if (request.getIsDefault() != null && request.getIsDefault()) {
                deliveryAddressRepository.clearDefaultAddress(userId);
            }

            DeliveryAddress savedAddress = deliveryAddressRepository.save(newAddress);
            log.info("Created clone address {} for user {} (original: {})", savedAddress.getId(), userId, addressId);

            return mapToDto(savedAddress);
        } else {
            // Если заказов нет - обычное редактирование
            if (request.getIsDefault() != null && request.getIsDefault()) {
                deliveryAddressRepository.clearDefaultAddress(userId);
            }

            address.setStreet(request.getStreet());
            address.setCity(request.getCity());
            address.setPostalCode(request.getPostalCode());
            address.setCountry(request.getCountry());
            address.setPhone(request.getPhone());
            address.setIsDefault(request.getIsDefault() != null ? request.getIsDefault() : false);

            DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
            log.info("Updated delivery address {} for user {}", addressId, userId);

            return mapToDto(savedAddress);
        }
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        DeliveryAddress address = deliveryAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        // Проверяем, есть ли заказы с этим адресом
        boolean hasOrders = orderRepository.existsByDeliveryAddressId(addressId);

        if (hasOrders) {
            // Если есть заказы - мягкое удаление (помечаем как технический и неактивный)
            log.info("Address {} has orders, marking as technical and inactive for user {}", addressId, userId);
            deliveryAddressRepository.markAsTechnical(addressId);
        } else {
            // Если заказов нет - обычное удаление
            deliveryAddressRepository.delete(address);
            log.info("Deleted delivery address {} for user {}", addressId, userId);
        }
    }

    @Transactional(readOnly = true)
    public DeliveryAddressDto getDefaultAddress(Long userId) {
        DeliveryAddress defaultAddress = deliveryAddressRepository
                .findByUserIdAndIsDefaultTrueAndIsTechnicalFalse(userId)
                .orElse(null);

        return defaultAddress != null ? mapToDto(defaultAddress) : null;
    }

    private DeliveryAddressDto mapToDto(DeliveryAddress address) {
        return DeliveryAddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .phone(address.getPhone())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .build();
    }
}
