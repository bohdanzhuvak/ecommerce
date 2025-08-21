package io.github.bohdanzhuvak.onlinestore.common.model;

public enum TransactionType {
    DEPOSIT,        // Пополнение
    WITHDRAW,       // Списание
    PURCHASE,       // Покупка заказа
    REFUND,         // Возврат средств
    ADMIN_ADJUSTMENT // Административная корректировка
}
