package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.user.application.service.WebUserOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.DeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.DeliveryAddressResponse;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UpdateDeliveryAddressRequest;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UpdateProfileRequest;
import io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.in.rest.resource.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "users-customer", description = "User profile management for customers")
@RestController
@RequestMapping("/api/v1/customer/users")
public class UserController {
  private final WebUserOrchestratorService webUserOrchestratorService;

  public UserController(WebUserOrchestratorService webUserOrchestratorService) {
    this.webUserOrchestratorService = webUserOrchestratorService;
  }

  @Operation(operationId = "getUserProfile", summary = "Get user profile", description = "Get profile information for the authenticated user")
  @GetMapping
  public ResponseEntity<UserResponse> getUserProfile(@CurrentUserId String userId) {
    User user = webUserOrchestratorService.getUserProfile(UserId.of(userId));
    return ResponseEntity.ok(UserResponse.from(user));
  }

  @Operation(operationId = "updateUserProfile", summary = "Update user profile", description = "Update profile information for the authenticated user")
  @PutMapping("profile")
  public ResponseEntity<UserResponse> updateProfile(@CurrentUserId String userId,
                                                    @RequestBody UpdateProfileRequest request) {
    try {
      User user = webUserOrchestratorService.updateUserProfile(
          UserId.of(userId),
          request.firstName(),
          request.lastName()
      );
      return ResponseEntity.ok(UserResponse.from(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "getDeliveryAddresses", summary = "Get saved delivery addresses", description = "Get delivery addresses for the authenticated user")
  @GetMapping("delivery-addresses")
  public ResponseEntity<List<DeliveryAddressResponse>> getDeliveryAddresses(@CurrentUserId String userId) {
    User user = webUserOrchestratorService.getUserProfile(UserId.of(userId));
    return ResponseEntity.ok(DeliveryAddressResponse.from(user));
  }

  @Operation(operationId = "deleteDeliveryAddress", summary = "Delete delivery address", description = "Delete delivery address by id for the authenticated user")
  @DeleteMapping("delivery-addresses/{id}")
  public ResponseEntity<Void> deleteDeliveryAddress(@CurrentUserId String userId, @PathVariable String id) {
    webUserOrchestratorService.deleteDeliveryAddress(UserId.of(userId), DeliveryAddressId.of(id));
    return ResponseEntity.ok().build();
  }

  @Operation(operationId = "addDeliveryAddress", summary = "Add delivery address", description = "Add delivery address for the authenticated user")
  @PostMapping("delivery-addresses")
  public ResponseEntity<DeliveryAddressResponse> addDeliveryAddress(@CurrentUserId String userId, @RequestBody DeliveryAddressRequest deliveryAddressRequest) {
    DeliveryAddress deliveryAddress = DeliveryAddress.create(
        deliveryAddressRequest.street(),
        deliveryAddressRequest.city(),
        deliveryAddressRequest.state(),
        deliveryAddressRequest.postalCode(),
        deliveryAddressRequest.country(),
        deliveryAddressRequest.recipientName()
    );
    DeliveryAddress deliveryAddressSaved = webUserOrchestratorService.addDeliveryAddress(UserId.of(userId), deliveryAddress, deliveryAddressRequest.isDefault());
    return ResponseEntity.ok(DeliveryAddressResponse.from(deliveryAddressSaved, deliveryAddressRequest.isDefault()));
  }

  @Operation(operationId = "updateDeliveryAddress", summary = "Update delivery address", description = "Update delivery address for the authenticated user")
  @PutMapping("delivery-addresses")
  public ResponseEntity<DeliveryAddressResponse> updateDeliveryAddress(@CurrentUserId String userId, @RequestBody UpdateDeliveryAddressRequest deliveryAddressRequest) {
    DeliveryAddress deliveryAddress = DeliveryAddress.restore(
        DeliveryAddressId.of(deliveryAddressRequest.id()),
        deliveryAddressRequest.street(),
        deliveryAddressRequest.city(),
        deliveryAddressRequest.state(),
        deliveryAddressRequest.postalCode(),
        deliveryAddressRequest.country(),
        deliveryAddressRequest.recipientName()
    );
    DeliveryAddress deliveryAddressSaved = webUserOrchestratorService.updateDeliveryAddress(UserId.of(userId), deliveryAddress, deliveryAddressRequest.isDefault());
    return ResponseEntity.ok(DeliveryAddressResponse.from(deliveryAddressSaved, deliveryAddressRequest.isDefault()));
  }

  @Operation(operationId = "deleteUserProfile", summary = "Delete user profile", description = "Delete user profile for current user")
  @DeleteMapping
  public ResponseEntity<Void> deleteUserProfile(@CurrentUserId String userId) {
    webUserOrchestratorService.deleteUserProfile(UserId.of(userId));
    return ResponseEntity.ok().build();
  }

}
