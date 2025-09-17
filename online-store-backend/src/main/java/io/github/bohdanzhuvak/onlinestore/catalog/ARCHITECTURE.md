# Catalog Bounded Context Architecture

## Package Structure

```
catalog/
├── domain/                    # Domain Layer
│   ├── Product.java          # Main product entity
│   ├── ProductId.java        # Value Object - Product ID
│   ├── Money.java            # Value Object - Money with currency
│   ├── CategoryId.java       # Value Object - Category ID
│   ├── ProductRepository.java # Repository interface
│   ├── ProductCreated.java   # Domain event
│   ├── ProductUpdated.java   # Domain event
│   └── ProductDeleted.java   # Domain event
├── api/                      # API Layer
│   ├── customer/               # API for customers
│   │   └── ProductCustomerController.java
│   └── admin/                # API for administrators
│       └── ProductAdminController.java
├── application/              # Application Layer
│   ├── customer/             # Customer Use Cases
│   │   ├── GetProductsUseCase.java
│   │   ├── GetProductUseCase.java
│   │   └── SearchProductsUseCase.java
│   ├── admin/                # Admin Use Cases
│   │   ├── CreateProductUseCase.java
│   │   ├── UpdateProductUseCase.java
│   │   ├── DeleteProductUseCase.java
│   │   └── GetAllProductsUseCase.java
│   └── ProductApplicationService.java
└── infrastructure/           # Infrastructure Layer
    ├── ProductEntity.java    # JPA entity
    ├── JpaProductRepository.java # JPA repository
    ├── ProductRepositoryImpl.java # Domain repository implementation
    └── ProductMapper.java    # Mapper between layers
```

## Layer Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    API Layer                                │
├─────────────────────────────────────────────────────────────┤
│  Customer Controller    │    Admin Controller                 │
│  - GetProducts        │    - CreateProduct                  │
│  - GetProduct         │    - UpdateProduct                  │
│  - SearchProducts     │    - DeleteProduct                  │
│  - GetAvailable       │    - GetAllProducts                 │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                Application Layer                            │
├─────────────────────────────────────────────────────────────┤
│              ProductApplicationService                      │
│  - Orchestrates Use Cases                                  │
│  - Transaction Management                                   │
│  - Cross-cutting Concerns                                   │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                  Domain Layer                               │
├─────────────────────────────────────────────────────────────┤
│  Product Entity      │  Value Objects    │  Domain Events   │
│  - Business Logic    │  - ProductId      │  - ProductCreated│
│  - Validation        │  - Money          │  - ProductUpdated│
│  - State Changes     │  - CategoryId     │  - ProductDeleted│
│                      │                   │                  │
│  ProductRepository (Interface)                             │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│              Infrastructure Layer                           │
├─────────────────────────────────────────────────────────────┤
│  JpaProductRepository │  ProductEntity    │  ProductMapper  │
│  - Data Access        │  - JPA Mapping    │  - DTO Mapping  │
│  - Queries            │  - Database       │  - Conversion   │
│  - Pagination         │  - Constraints    │  - Validation   │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow

### Product Creation (Admin)

1. **Admin Controller** receives HTTP POST request
2. **ProductApplicationService** orchestrates the process
3. **CreateProductUseCase** executes business logic
4. **ProductRepository** saves to DB through **ProductRepositoryImpl**
5. **ProductMapper** converts between domain objects and JPA entities
6. Created product is returned

### Product Retrieval (Customer)

1. **Customer Controller** receives HTTP GET request
2. **ProductApplicationService** delegates to **GetProductsUseCase**
3. **ProductRepository** retrieves data from DB
4. **ProductMapper** converts JPA entities to domain objects
5. List of products is returned

## DDD Principles

### 1. Business Logic Encapsulation

- All business logic is in the domain layer
- Entities contain behavior, not just data
- Value Objects provide validation and encapsulation

### 2. Separation of Concerns

- **Domain**: business rules and logic
- **Application**: orchestration and transactions
- **API**: HTTP interface
- **Infrastructure**: persistence and external services

### 3. Dependency Inversion

- Domain layer doesn't depend on infrastructure
- Repository is defined in domain, implemented in infrastructure
- Use Cases depend on abstractions, not concrete implementations

### 4. Domain Events

- Events enable loose coupling between contexts
- Other bounded contexts can subscribe to catalog events
- Asynchronous event processing

## Extensibility

### Adding New Use Cases

1. Create Use Case in appropriate package (customer/admin)
2. Add method to ProductApplicationService
3. Add endpoint to corresponding Controller

### Adding New Fields to Product

1. Update domain model Product
2. Update ProductEntity in infrastructure
3. Update ProductMapper
4. Create database migration

### Integration with Other Contexts

- Use domain events to notify other contexts
- Define clear interfaces for interaction
- Avoid direct dependencies between contexts
