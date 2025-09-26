package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.delivery.DeliveryAddressResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.DeliveryAddressService;
import io.github.bohdanzhuvak.onlinestore.legacy.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/delivery-addresses")
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddressController {

  private final DeliveryAddressService deliveryAddressService;

  @GetMapping
  public ResponseEntity<List<DeliveryAddressResponse>> getUserAddresses(@CurrentUser UserPrincipal user) {
    List<DeliveryAddressResponse> addresses = deliveryAddressService.getUserAddresses(user.id());
    return ResponseEntity.ok(addresses);
  }

  @PostMapping
  public ResponseEntity<DeliveryAddressResponse> createAddress(@CurrentUser UserPrincipal user,
                                                               @Valid @RequestBody CreateDeliveryAddressRequest request) {
    DeliveryAddressResponse address = deliveryAddressService.createAddress(user.id(), request);
    return ResponseEntity.ok(address);
  }

  @PutMapping("/{addressId}")
  public ResponseEntity<DeliveryAddressResponse> updateAddress(@CurrentUser UserPrincipal user,
                                                               @PathVariable Long addressId,
                                                               @Valid @RequestBody CreateDeliveryAddressRequest request) {
    DeliveryAddressResponse address = deliveryAddressService.updateAddress(user.id(), addressId, request);
    return ResponseEntity.ok(address);
  }

  @DeleteMapping("/{addressId}")
  public ResponseEntity<Void> deleteAddress(@CurrentUser UserPrincipal user,
                                            @PathVariable Long addressId) {
    deliveryAddressService.deleteAddress(user.id(), addressId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/default")
  public ResponseEntity<DeliveryAddressResponse> getDefaultAddress(@CurrentUser UserPrincipal user) {
    DeliveryAddressResponse address = deliveryAddressService.getDefaultAddress(user.id());
    return ResponseEntity.ok(address);
  }
}
