# Order Bounded Context Architecture

## Layer Structure

```
order/
├── domain/                    # Pure business logic
│   ├── Order.java             # Aggregate root
│   ├── OrderId.java           # Value Object
│   ├── UserId.java            # Value Object
│   ├── ProductId.java         # Value Object
│   ├── Money.java             # Value Object
│   ├── OrderItem.java         # Value Object
│   ├── OrderStatus.java       # Value Object (Enum)
│   ├── OrderCreated.java      # Domain Event
│   ├── OrderPaid.java         # Domain Event
│   ├── OrderCancelled.java    # Domain Event
│   ├── OrderShipped.java      # Domain Event
│   ├── OrderDelivered.java    # Domain Event
│   └── OrderRepository.java   # Repository interface
├── application/              # Use cases and orchestration
│   ├── customer/             # Customer use cases
│   │   ├── CreateOrderUseCase.java
│   │   ├── GetOrdersUseCase.java
│   │   ├── GetOrderUseCase.java
│   │   ├── PayOrderUseCase.java
│   │   └── CancelOrderUseCase.java
│   ├── admin/                # Admin use cases
│   │   ├── GetAllOrdersUseCase.java
│   │   └── UpdateOrderStatusUseCase.java
│   ├── ports/                # Integration ports
│   │   ├── CartService.java
│   │   ├── DeliveryAddressService.java
│   │   └── BalanceService.java
│   └── OrderApplicationService.java
├── infrastructure/           # External concerns
│   ├── OrderEntity.java      # JPA entity
│   ├── OrderItemEntity.java  # JPA entity
│   ├── JpaOrderRepository.java # Spring Data JPA
│   ├── OrderMapper.java      # Domain-Entity mapping
│   └── OrderRepositoryImpl.java # Repository implementation
└── api/                      # REST controllers
    ├── customer/             # Customer API
    │   └── OrderController.java
    └── admin/                # Admin API
        └── OrderAdminController.java
```

## Data Flow

### Order Creation Flow

1. **API Layer**: `OrderController.createOrder()` receives order creation request
2. **Application Layer**: `CreateOrderUseCase.execute()` orchestrates the process
3. **Integration**: Validates delivery address via `DeliveryAddressService`
4. **Integration**: Gets cart items via `CartService`
5. **Domain Layer**: `Order` constructor validates business rules
6. **Infrastructure Layer**: `OrderRepositoryImpl.save()` persists to database
7. **Integration**: Clears cart via `CartService`
8. **Domain Event**: `OrderCreated` event is published

### Order Payment Flow

1. **API Layer**: `OrderController.payOrder()` receives payment request
2. **Application Layer**: `PayOrderUseCase.execute()` orchestrates the process
3. **Domain Layer**: Validates order can be paid
4. **Integration**: Checks balance via `BalanceService`
5. **Domain Layer**: Updates order status
6. **Infrastructure Layer**: `OrderRepositoryImpl.save()` persists changes
7. **Integration**: Deducts balance via `BalanceService`
8. **Domain Event**: `OrderPaid` event is published

## DDD Principles

### Aggregate Root

- **Order** is the single aggregate root
- All order operations go through the Order entity
- OrderId is the aggregate identifier

### Value Objects

- **OrderId**: Immutable identifier
- **UserId**: User reference (from User context)
- **ProductId**: Product reference (from Catalog context)
- **Money**: Price with currency validation
- **OrderItem**: Order line item with business rules
- **OrderStatus**: Enum with state transition logic

### Domain Events

- Events are published when significant business operations occur
- Other bounded contexts can subscribe to these events
- Events enable loose coupling between contexts

### Repository Pattern

- `OrderRepository` interface in domain layer
- `OrderRepositoryImpl` in infrastructure layer
- Abstraction allows different storage implementations

### Integration Ports

- **CartService**: Interface for cart operations
- **DeliveryAddressService**: Interface for address validation
- **BalanceService**: Interface for payment processing
- Ports enable integration with other bounded contexts

## State Management

### Order Status Transitions

```
PENDING → PAID (payment)
PENDING → CANCELLED (cancellation)
PAID → SHIPPED (admin action)
SHIPPED → DELIVERED (admin action)
```

### Business Rules

- Orders can only be paid if status is PENDING
- Orders can only be cancelled if status is PENDING
- Orders can only be shipped if status is PAID
- Orders can only be delivered if status is SHIPPED

## Extensibility

### Adding New Order Statuses

1. Add new enum value to `OrderStatus`
2. Update business logic in `Order` entity
3. Add validation rules as needed

### Adding New Order Operations

1. Add method to `Order` entity
2. Create use case in application layer
3. Add API endpoint if needed

### Integration with Other Contexts

- Other contexts can subscribe to Order domain events
- Order context uses ports to integrate with other contexts
- Loose coupling enables independent evolution

## Security Considerations

- User authorization for order access
- Balance verification for payments
- State transition validation
- Input validation at all layers
- Audit logging for sensitive operations

## Testing Strategy

- **Unit Tests**: Domain entities and value objects
- **Integration Tests**: Repository implementations and external services
- **API Tests**: Controller endpoints
- **End-to-End Tests**: Complete order workflows
- **Contract Tests**: Integration port implementations
