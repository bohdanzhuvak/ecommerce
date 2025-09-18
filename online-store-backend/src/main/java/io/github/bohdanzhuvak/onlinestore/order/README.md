# Order Bounded Context

This bounded context handles order management, payment processing, and order status tracking in the online store system.

## Overview

The Order bounded context is responsible for:

- Order creation from cart items
- Order payment processing
- Order status management (PENDING, PAID, SHIPPED, DELIVERED, CANCELLED)
- Order cancellation and refunds
- Integration with Cart, User, and Balance contexts

## Key Features

- **Order Creation**: Convert cart items to orders with delivery address
- **Payment Processing**: Process payments using user balance
- **Status Management**: Track order lifecycle with proper state transitions
- **Cancellation**: Cancel orders with automatic refunds for paid orders
- **Admin Management**: View and update order statuses

## Domain Rules

- Orders must have at least one item
- Orders can only be paid if status is PENDING
- Orders can only be cancelled if status is PENDING
- Orders can only be shipped if status is PAID
- Orders can only be delivered if status is SHIPPED
- Users can only access their own orders
- Payment requires sufficient user balance

## Business Operations

### Customer Operations

- **Create Order**: Convert cart to order with delivery address
- **Get Orders**: List user's orders
- **Get Order**: View specific order details
- **Pay Order**: Process payment using user balance
- **Cancel Order**: Cancel order with automatic refund

### Admin Operations

- **Get All Orders**: List orders with filtering and pagination
- **Update Order Status**: Change order status (ship, deliver, etc.)

## Domain Model

### Entities

- **Order**: Main aggregate root containing order information and items

### Value Objects

- **OrderId**: Unique identifier for orders
- **UserId**: User identifier (from User context)
- **ProductId**: Product identifier (from Catalog context)
- **Money**: Price representation with currency
- **OrderItem**: Order line item with product details and quantity
- **OrderStatus**: Enum for order states

### Domain Events

- **OrderCreated**: Published when a new order is created
- **OrderPaid**: Published when an order is paid
- **OrderCancelled**: Published when an order is cancelled
- **OrderShipped**: Published when an order is shipped
- **OrderDelivered**: Published when an order is delivered

## API Endpoints

### Customer Endpoints

- `POST /api/customer/orders` - Create order from cart
- `GET /api/customer/orders` - Get user's orders
- `GET /api/customer/orders/{orderId}` - Get order details
- `POST /api/customer/orders/{orderId}/pay` - Pay order
- `PUT /api/customer/orders/{orderId}/cancel` - Cancel order

### Admin Endpoints

- `GET /api/admin/orders` - Get all orders (with filtering)
- `PUT /api/admin/orders/{orderId}/status` - Update order status

## Integration with Other Contexts

- **Cart Context**: Uses CartService to get cart items and clear cart
- **User Context**: Uses UserId for order ownership
- **Balance Context**: Uses BalanceService for payment processing
- **Delivery Context**: Uses DeliveryAddressService for address validation

## Security Considerations

- Users can only access their own orders
- Payment processing requires balance verification
- Order status transitions follow business rules
- Delivery address validation ensures data integrity

## State Transitions

```
PENDING → PAID (payment)
PENDING → CANCELLED (cancellation)
PAID → SHIPPED (admin action)
SHIPPED → DELIVERED (admin action)
```

## Future Enhancements

- Order tracking and notifications
- Partial order cancellation
- Order history and analytics
- Integration with external payment systems
- Order fulfillment workflow
- Inventory management integration
