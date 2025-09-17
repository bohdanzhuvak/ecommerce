# Cart Bounded Context Architecture

## Package Structure

```
cart/
├── domain/                    # Domain Layer
│   ├── Cart.java             # Main cart entity
│   ├── CartId.java           # Value Object - Cart ID
│   ├── UserId.java           # Value Object - User ID
│   ├── CartItem.java         # Value Object - Cart Item
│   ├── CartRepository.java   # Repository interface
│   ├── CartItemAdded.java    # Domain event
│   ├── CartItemRemoved.java  # Domain event
│   ├── CartItemUpdated.java  # Domain event
│   └── CartCleared.java      # Domain event
├── api/                      # API Layer
│   └── customer/             # API for customers
│       └── CartController.java
├── application/              # Application Layer
│   ├── customer/             # Customer Use Cases
│   │   ├── GetCartUseCase.java
│   │   ├── AddItemToCartUseCase.java
│   │   ├── UpdateCartItemUseCase.java
│   │   ├── RemoveItemFromCartUseCase.java
│   │   └── ClearCartUseCase.java
│   └── CartApplicationService.java
└── infrastructure/           # Infrastructure Layer
    ├── CartEntity.java       # JPA entity
    ├── CartItemEntity.java   # JPA entity
    ├── JpaCartRepository.java # JPA repository
    ├── CartRepositoryImpl.java # Domain repository implementation
    └── CartMapper.java       # Mapper between layers
```

## Layer Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    API Layer                                │
├─────────────────────────────────────────────────────────────┤
│              Customer Controller                             │
│  - GetCart                                                  │
│  - AddItemToCart                                            │
│  - UpdateCartItem                                           │
│  - RemoveItemFromCart                                       │
│  - ClearCart                                                │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                Application Layer                            │
├─────────────────────────────────────────────────────────────┤
│              CartApplicationService                         │
│  - Orchestrates Use Cases                                  │
│  - Transaction Management                                   │
│  - Cross-cutting Concerns                                   │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                  Domain Layer                               │
├─────────────────────────────────────────────────────────────┤
│  Cart Entity        │  Value Objects    │  Domain Events    │
│  - Business Logic   │  - CartId         │  - CartItemAdded  │
│  - Validation       │  - UserId         │  - CartItemRemoved│
│  - State Changes    │  - CartItem       │  - CartItemUpdated│
│                     │                   │  - CartCleared    │
│  CartRepository (Interface)                               │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│              Infrastructure Layer                           │
├─────────────────────────────────────────────────────────────┤
│  JpaCartRepository  │  CartEntity       │  CartMapper       │
│  - Data Access      │  - JPA Mapping    │  - DTO Mapping    │
│  - Queries          │  - Database       │  - Conversion     │
│  - User Scoping     │  - Constraints    │  - Validation     │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow

### Add Item to Cart (Customer)

1. **Customer Controller** receives HTTP POST request
2. **CartApplicationService** orchestrates the process
3. **AddItemToCartUseCase** executes business logic
4. **CartRepository** finds or creates cart for user
5. **Cart** domain object adds item with validation
6. **CartRepository** saves cart through **CartRepositoryImpl**
7. **CartMapper** converts between domain objects and JPA entities
8. Updated cart is returned

### Get Cart Contents (Customer)

1. **Customer Controller** receives HTTP GET request
2. **CartApplicationService** delegates to **GetCartUseCase**
3. **CartRepository** retrieves cart by user ID
4. **CartMapper** converts JPA entities to domain objects
5. Cart contents are returned

## DDD Principles

### 1. Business Logic Encapsulation

- All business logic is in the domain layer
- Cart entity contains behavior for managing items
- Value Objects provide validation and encapsulation
- Cart operations are atomic and consistent

### 2. Separation of Concerns

- **Domain**: cart business rules and logic
- **Application**: orchestration and transactions
- **API**: HTTP interface for customers only
- **Infrastructure**: persistence and external services

### 3. Dependency Inversion

- Domain layer doesn't depend on infrastructure
- Repository is defined in domain, implemented in infrastructure
- Use Cases depend on abstractions, not concrete implementations

### 4. Domain Events

- Events enable loose coupling between contexts
- Other bounded contexts can subscribe to cart events
- Asynchronous event processing for better performance

## Security and Privacy

### User Isolation

- Each user can only access their own cart
- Cart operations are scoped to authenticated user
- No cross-user data access

### No Admin Access

- Administrators cannot view or modify user carts
- Privacy protection for customer data
- Cart data is only accessible by the cart owner

## Integration Points

### With Catalog Context

- Uses `ProductId` and `Money` from catalog domain
- Cart items reference products by ID
- Price information is captured at add time

### With Order Context

- Cart contents can be converted to order items
- Cart is typically cleared after successful order creation
- Order creation validates cart contents

### With UserAccount Context

- Uses `UserId` for cart ownership
- Cart lifecycle tied to user account
- User deletion should clear associated cart

## Extensibility

### Adding New Cart Operations

1. Create Use Case in customer package
2. Add method to CartApplicationService
3. Add endpoint to CartController

### Adding New Cart Item Properties

1. Update CartItem domain object
2. Update CartItemEntity in infrastructure
3. Update CartMapper
4. Create database migration

### Integration with External Services

- Use domain events for external notifications
- Implement event handlers in infrastructure layer
- Maintain loose coupling with external systems
