package io.github.bohdanzhuvak.onlinestore.order.application.ports.out;

import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
  /**
   * Saves an order
   *
   * @param order the order to save
   * @return the saved order
   */
  Order save(Order order);

  /**
   * Finds an order by ID
   *
   * @param id the order ID
   * @return the order if found, empty otherwise
   */
  Optional<Order> findById(OrderId id);

  /**
   * Finds orders by user ID
   *
   * @param userId the user ID
   * @return list of orders for the user
   */
  List<Order> findByUserId(UserId userId);

  /**
   * Finds orders by status
   *
   * @param status the order status
   * @return list of orders with the specified status
   */
  List<Order> findByStatus(OrderStatus status);

  /**
   * Finds orders by user ID and status
   *
   * @param userId the user ID
   * @param status the order status
   * @return list of orders for the user with the specified status
   */
  List<Order> findByUserIdAndStatus(UserId userId, OrderStatus status);

  /**
   * Finds all orders
   *
   * @return list of all orders
   */
  List<Order> findAll();

  /**
   * Finds orders with pagination
   *
   * @param offset the offset
   * @param limit  the limit
   * @return list of orders with pagination
   */
  List<Order> findAll(int offset, int limit);

  /**
   * Finds orders by user ID with pagination
   *
   * @param userId the user ID
   * @param offset the offset
   * @param limit  the limit
   * @return list of orders for the user with pagination
   */
  List<Order> findByUserId(UserId userId, int offset, int limit);

  /**
   * Finds orders by status with pagination
   *
   * @param status the order status
   * @param offset the offset
   * @param limit  the limit
   * @return list of orders with the specified status and pagination
   */
  List<Order> findByStatus(OrderStatus status, int offset, int limit);

  /**
   * Counts total orders
   *
   * @return total number of orders
   */
  long count();

  /**
   * Counts orders by user ID
   *
   * @param userId the user ID
   * @return number of orders for the user
   */
  long countByUserId(UserId userId);

  /**
   * Counts orders by status
   *
   * @param status the order status
   * @return number of orders with the specified status
   */
  long countByStatus(OrderStatus status);

  /**
   * Checks if an order exists by ID
   *
   * @param id the order ID
   * @return true if order exists, false otherwise
   */
  boolean existsById(OrderId id);

  /**
   * Deletes an order
   *
   * @param id the order ID
   */
  void delete(OrderId id);
}
