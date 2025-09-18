package io.github.bohdanzhuvak.onlinestore.order.application.ports;

public interface DeliveryAddressService {
  /**
   * Checks if delivery address exists and belongs to user
   *
   * @param addressId the address ID
   * @param userId    the user ID
   * @return true if address exists and belongs to user
   */
  boolean existsAndBelongsToUser(String addressId, String userId);
}
