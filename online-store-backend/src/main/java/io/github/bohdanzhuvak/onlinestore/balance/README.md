# Balance Bounded Context

## Overview

The Balance bounded context manages user account balances and financial transactions within the online store system. It
provides secure and auditable financial operations including deposits, withdrawals, and balance adjustments.

## Key Features

- **Balance Management**: Track user account balances with currency support
- **Transaction History**: Complete audit trail of all financial operations
- **Transaction Types**: Support for deposits, withdrawals, purchases, refunds, and admin adjustments
- **Security**: Validation of sufficient funds before withdrawals
- **Audit Trail**: Immutable transaction records with timestamps and descriptions

## Domain Model

### Entities

#### Balance

- **UserId**: Unique identifier for the user
- **Amount**: Current balance amount with currency
- **LastUpdated**: Timestamp of last balance update

#### Transaction

- **TransactionId**: Unique identifier for the transaction
- **UserId**: User who performed the transaction
- **Type**: Type of transaction (DEPOSIT, WITHDRAW, PURCHASE, REFUND, ADMIN_ADJUSTMENT)
- **Amount**: Transaction amount with currency
- **BalanceAfter**: Balance after the transaction
- **Description**: Human-readable description
- **OrderId**: Optional reference to related order
- **CreatedAt**: Transaction timestamp

### Value Objects

- **UserId**: User identifier with validation
- **TransactionId**: Transaction identifier with validation
- **Money**: Amount with currency, supports arithmetic operations
- **TransactionType**: Enum for transaction types

### Domain Events

- **BalanceDeposited**: Published when balance is increased
- **BalanceWithdrawn**: Published when balance is decreased
- **InsufficientFunds**: Published when withdrawal fails due to insufficient funds

## Business Rules

1. **Non-negative Balance**: User balance cannot be negative
2. **Sufficient Funds**: Withdrawals require sufficient balance
3. **Currency Consistency**: All operations must use the same currency
4. **Immutable Transactions**: Once created, transactions cannot be modified
5. **Audit Trail**: All balance changes must be recorded as transactions

## Use Cases

### Customer Operations

- **GetBalance**: Retrieve current user balance
- **Deposit**: Add funds to user account
- **Withdraw**: Remove funds from user account (with validation)
- **GetTransactions**: View transaction history

### Admin Operations

- **AdjustBalance**: Admin adjustment of user balance
- **GetAllTransactions**: View all transactions with filtering

## API Endpoints

### Customer Endpoints

- `GET /api/customer/balance?userId={userId}` - Get user balance
- `POST /api/customer/balance/deposit` - Deposit funds
- `POST /api/customer/balance/withdraw` - Withdraw funds
- `GET /api/customer/balance/transactions?userId={userId}&offset={offset}&limit={limit}` - Get transaction history

### Admin Endpoints

- `POST /api/admin/balance/adjust` - Adjust user balance
- `GET /api/admin/balance/transactions?userId={userId}&type={type}&offset={offset}&limit={limit}` - Get all transactions

## Integration

### Inbound Integration

- **Order Context**: Receives payment requests and refund notifications
- **User Context**: Validates user existence

### Outbound Integration

- **Order Context**: Provides balance validation for payments
- **Notification Context**: Publishes domain events for notifications

## Security Considerations

1. **Authorization**: Only users can access their own balance and transactions
2. **Validation**: All amounts are validated for non-negative values
3. **Audit**: Complete transaction history for compliance
4. **Currency**: Strict currency validation to prevent conversion errors

## Database Schema

### balances table

- `user_id` (VARCHAR, PRIMARY KEY)
- `amount` (DECIMAL(10,2), NOT NULL)
- `currency` (VARCHAR(3), NOT NULL)
- `last_updated` (TIMESTAMP, NOT NULL)

### balance_transactions table

- `id` (VARCHAR, PRIMARY KEY)
- `user_id` (VARCHAR, NOT NULL)
- `type` (VARCHAR(20), NOT NULL)
- `amount` (DECIMAL(10,2), NOT NULL)
- `currency` (VARCHAR(3), NOT NULL)
- `balance_after` (DECIMAL(10,2), NOT NULL)
- `description` (VARCHAR(500))
- `order_id` (VARCHAR)
- `created_at` (TIMESTAMP, NOT NULL)

## Future Enhancements

- **Multi-currency Support**: Support for multiple currencies with conversion
- **Transaction Limits**: Daily/monthly transaction limits
- **Fraud Detection**: Automated fraud detection for suspicious transactions
- **External Payment Integration**: Integration with external payment providers
- **Transaction Fees**: Support for transaction fees and commissions
