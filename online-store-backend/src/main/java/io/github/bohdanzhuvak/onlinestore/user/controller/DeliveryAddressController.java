package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.common.auth.security.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.CreateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.DeliveryAddressDto;
import io.github.bohdanzhuvak.onlinestore.user.service.DeliveryAddressService;
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
  public ResponseEntity<List<DeliveryAddressDto>> getUserAddresses(@CurrentUser UserPrincipal user) {
    List<DeliveryAddressDto> addresses = deliveryAddressService.getUserAddresses(user.getId());
    return ResponseEntity.ok(addresses);
  }

  @PostMapping
  public ResponseEntity<DeliveryAddressDto> createAddress(@CurrentUser UserPrincipal user,
                                                          @Valid @RequestBody CreateDeliveryAddressRequest request) {
    DeliveryAddressDto address = deliveryAddressService.createAddress(user.getId(), request);
    return ResponseEntity.ok(address);
  }

  @PutMapping("/{addressId}")
  public ResponseEntity<DeliveryAddressDto> updateAddress(@CurrentUser UserPrincipal user,
                                                          @PathVariable Long addressId,
                                                          @Valid @RequestBody CreateDeliveryAddressRequest request) {
    DeliveryAddressDto address = deliveryAddressService.updateAddress(user.getId(), addressId, request);
    return ResponseEntity.ok(address);
  }

  @DeleteMapping("/{addressId}")
  public ResponseEntity<Void> deleteAddress(@CurrentUser UserPrincipal user,
                                            @PathVariable Long addressId) {
    deliveryAddressService.deleteAddress(user.getId(), addressId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/default")
  public ResponseEntity<DeliveryAddressDto> getDefaultAddress(@CurrentUser UserPrincipal user) {
    DeliveryAddressDto address = deliveryAddressService.getDefaultAddress(user.getId());
    return ResponseEntity.ok(address);
  }
}
