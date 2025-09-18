# User Bounded Context Architecture

## Layer Structure

```
user/
├── domain/                    # Pure business logic
│   ├── User.java             # Aggregate root
│   ├── UserId.java           # Value Object
│   ├── Email.java            # Value Object
│   ├── Password.java         # Value Object
│   ├── UserRole.java         # Value Object (Enum)
│   ├── UserCreated.java      # Domain Event
│   ├── UserUpdated.java      # Domain Event
│   ├── UserActivated.java    # Domain Event
│   ├── UserDeactivated.java  # Domain Event
│   └── UserRepository.java   # Repository interface
├── application/              # Use cases and orchestration
│   ├── customer/             # Customer use cases
│   │   ├── RegisterUserUseCase.java
│   │   ├── GetUserProfileUseCase.java
│   │   ├── UpdateUserProfileUseCase.java
│   │   └── ChangePasswordUseCase.java
│   ├── admin/                # Admin use cases
│   │   ├── CreateUserUseCase.java
│   │   ├── GetAllUsersUseCase.java
│   │   ├── ActivateUserUseCase.java
│   │   └── DeactivateUserUseCase.java
│   └── UserApplicationService.java
├── infrastructure/           # External concerns
│   ├── UserEntity.java       # JPA entity
│   ├── JpaUserRepository.java # Spring Data JPA
│   ├── UserMapper.java       # Domain-Entity mapping
│   └── UserRepositoryImpl.java # Repository implementation
└── api/                      # REST controllers
    ├── customer/             # Customer API
    │   └── UserController.java
    └── admin/                # Admin API
        └── UserAdminController.java
```

## Data Flow

### User Registration Flow

1. **API Layer**: `UserController.register()` receives registration request
2. **Application Layer**: `RegisterUserUseCase.execute()` orchestrates the process
3. **Domain Layer**: `User` constructor validates business rules
4. **Infrastructure Layer**: `UserRepositoryImpl.save()` persists to database
5. **Domain Event**: `UserCreated` event is published

### User Profile Update Flow

1. **API Layer**: `UserController.updateProfile()` receives update request
2. **Application Layer**: `UpdateUserProfileUseCase.execute()` orchestrates the process
3. **Domain Layer**: `User.updateProfile()` validates and updates profile
4. **Infrastructure Layer**: `UserRepositoryImpl.save()` persists changes
5. **Domain Event**: `UserUpdated` event is published

## DDD Principles

### Aggregate Root

- **User** is the single aggregate root
- All user operations go through the User entity
- UserId is the aggregate identifier

### Value Objects

- **UserId**: Immutable identifier
- **Email**: Validated email with business rules
- **Password**: Secure password handling
- **UserRole**: Enum with business logic

### Domain Events

- Events are published when significant business operations occur
- Other bounded contexts can subscribe to these events
- Events enable loose coupling between contexts

### Repository Pattern

- `UserRepository` interface in domain layer
- `UserRepositoryImpl` in infrastructure layer
- Abstraction allows different storage implementations

## Extensibility

### Adding New User Roles

1. Add new enum value to `UserRole`
2. Update business logic in `User` entity
3. Add validation rules as needed

### Adding New User Attributes

1. Add field to `User` entity
2. Update `UserEntity` JPA mapping
3. Update `UserMapper` for conversion
4. Add API endpoints if needed

### Integration with Other Contexts

- Other contexts can subscribe to User domain events
- UserId can be used as foreign key in other contexts
- User information can be shared through events or service calls

## Security Considerations

- Password hashing should be implemented in production
- Email validation prevents invalid data
- Role-based access control in API layer
- Input validation at all layers
- Audit logging for sensitive operations

## Testing Strategy

- **Unit Tests**: Domain entities and value objects
- **Integration Tests**: Repository implementations
- **API Tests**: Controller endpoints
- **End-to-End Tests**: Complete user workflows
