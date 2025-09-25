package io.github.bohdanzhuvak.onlinestore.delivery.application.port.out;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {
  /**
   * Saves a delivery
   *
   * @param delivery the delivery to save
   * @return the saved delivery
   */
  Delivery save(Delivery delivery);

  /**
   * Finds delivery by ID
   *
   * @param id the delivery ID
   * @return the delivery if found, empty otherwise
   */
  Optional<Delivery> findById(DeliveryId id);

  /**
   * Finds delivery by order ID
   *
   * @param orderId the order ID
   * @return the delivery if found, empty otherwise
   */
  Optional<Delivery> findByOrderId(OrderId orderId);

  /**
   * Finds deliveries by user ID
   *
   * @param userId the user ID
   * @return list of deliveries for the user
   */
  List<Delivery> findByUserId(UserId userId);

  /**
   * Finds deliveries by status
   *
   * @param status the delivery status
   * @return list of deliveries with the specified status
   */
  List<Delivery> findByStatus(DeliveryStatus status);

  /**
   * Finds deliveries by tracking number
   *
   * @param trackingNumber the tracking number
   * @return the delivery if found, empty otherwise
   */
  Optional<Delivery> findByTrackingNumber(TrackingNumber trackingNumber);

  /**
   * Finds deliveries by user ID with pagination
   *
   * @param userId the user ID
   * @param offset the offset
   * @param limit  the limit
   * @return list of deliveries for the user with pagination
   */
  List<Delivery> findByUserId(UserId userId, int offset, int limit);

  /**
   * Finds deliveries by status with pagination
   *
   * @param status the delivery status
   * @param offset the offset
   * @param limit  the limit
   * @return list of deliveries with the specified status and pagination
   */
  List<Delivery> findByStatus(DeliveryStatus status, int offset, int limit);

  /**
   * Finds all deliveries with pagination
   *
   * @param offset the offset
   * @param limit  the limit
   * @return list of deliveries with pagination
   */
  List<Delivery> findAll(int offset, int limit);

  /**
   * Counts deliveries by user ID
   *
   * @param userId the user ID
   * @return number of deliveries for the user
   */
  long countByUserId(UserId userId);

  /**
   * Counts deliveries by status
   *
   * @param status the delivery status
   * @return number of deliveries with the specified status
   */
  long countByStatus(DeliveryStatus status);

  /**
   * Counts total deliveries
   *
   * @return total number of deliveries
   */
  long count();

  /**
   * Checks if delivery exists by ID
   *
   * @param id the delivery ID
   * @return true if delivery exists, false otherwise
   */
  boolean existsById(DeliveryId id);

  /**
   * Checks if delivery exists by order ID
   *
   * @param orderId the order ID
   * @return true if delivery exists, false otherwise
   */
  boolean existsByOrderId(OrderId orderId);

  /**
   * Deletes delivery
   *
   * @param id the delivery ID
   */
  void delete(DeliveryId id);
}
