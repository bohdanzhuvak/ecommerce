package io.github.bohdanzhuvak.onlinestore.balance.domain;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
  /**
   * Saves a transaction
   *
   * @param transaction the transaction to save
   * @return the saved transaction
   */
  Transaction save(Transaction transaction);

  /**
   * Finds transaction by ID
   *
   * @param id the transaction ID
   * @return the transaction if found, empty otherwise
   */
  Optional<Transaction> findById(TransactionId id);

  /**
   * Finds transactions by user ID
   *
   * @param userId the user ID
   * @return list of transactions for the user
   */
  List<Transaction> findByUserId(UserId userId);

  /**
   * Finds transactions by user ID with pagination
   *
   * @param userId the user ID
   * @param offset the offset
   * @param limit  the limit
   * @return list of transactions for the user with pagination
   */
  List<Transaction> findByUserId(UserId userId, int offset, int limit);

  /**
   * Finds transactions by type
   *
   * @param type the transaction type
   * @return list of transactions with the specified type
   */
  List<Transaction> findByType(TransactionType type);

  /**
   * Finds transactions by user ID and type
   *
   * @param userId the user ID
   * @param type   the transaction type
   * @return list of transactions for the user with the specified type
   */
  List<Transaction> findByUserIdAndType(UserId userId, TransactionType type);

  /**
   * Finds transactions by order ID
   *
   * @param orderId the order ID
   * @return list of transactions related to the order
   */
  List<Transaction> findByOrderId(String orderId);

  /**
   * Counts transactions by user ID
   *
   * @param userId the user ID
   * @return number of transactions for the user
   */
  long countByUserId(UserId userId);

  /**
   * Counts transactions by type
   *
   * @param type the transaction type
   * @return number of transactions with the specified type
   */
  long countByType(TransactionType type);

  /**
   * Checks if transaction exists by ID
   *
   * @param id the transaction ID
   * @return true if transaction exists, false otherwise
   */
  boolean existsById(TransactionId id);

  /**
   * Deletes transaction
   *
   * @param id the transaction ID
   */
  void delete(TransactionId id);
}
