# Auth Bounded Context Architecture

## Architecture Overview

The Auth bounded context follows Domain-Driven Design principles with clear separation of concerns across layers,
focusing on user authentication and authorization.

```
┌─────────────────────────────────────────────────────────────┐
│                        API Layer                            │
├─────────────────────────────────────────────────────────────┤
│  Customer Controller    │    Admin Controller              │
│  - Login               │    - Get All Users               │
│  - Register            │    - Activate User               │
│  - Refresh Token       │    - Deactivate User             │
│  - Get Current User    │                                  │
│  - Logout              │                                  │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                        │
├─────────────────────────────────────────────────────────────┤
│  AuthApplicationService                                     │
│  ├── Customer Use Cases                                    │
│  │   ├── RegisterUserUseCase                              │
│  │   ├── LoginUserUseCase                                 │
│  │   ├── GetUserUseCase                                   │
│  │   └── UpdateUserProfileUseCase                         │
│  └── Admin Use Cases                                       │
│      ├── GetAllUsersUseCase                               │
│      ├── ActivateUserUseCase                              │
│      └── DeactivateUserUseCase                            │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                           │
├─────────────────────────────────────────────────────────────┤
│  Entities: User                                            │
│  Value Objects: UserId, Username, Email, Password, AccessToken, RefreshToken │
│  Enums: UserRole                                           │
│  Domain Events: UserRegistered, UserLoggedIn, UserLoggedOut, UserActivated, UserDeactivated │
│  Repositories: UserRepository                              │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                   Infrastructure Layer                      │
├─────────────────────────────────────────────────────────────┤
│  JPA Entity: UserEntity                                    │
│  Repository: JpaUserRepository                             │
│  Mapper: UserMapper                                        │
│  Repository Implementation: UserRepositoryImpl             │
└─────────────────────────────────────────────────────────────┘
```

## Layer Responsibilities

### API Layer

- **Customer Controller**: Handles user authentication operations
- **Admin Controller**: Handles administrative user operations
- **Request/Response Mapping**: Converts between HTTP and domain objects
- **Error Handling**: Provides appropriate HTTP status codes
- **Security**: Handles JWT token generation and validation

### Application Layer

- **Use Cases**: Encapsulate business operations
- **Transaction Management**: Ensures data consistency
- **Orchestration**: Coordinates between domain and infrastructure
- **Validation**: Business rule validation
- **Token Management**: JWT token generation and validation

### Domain Layer

- **Entities**: User aggregate
- **Value Objects**: Immutable objects with validation
- **Business Rules**: Encapsulated in domain objects
- **Domain Events**: Published for integration

### Infrastructure Layer

- **Persistence**: JPA entity and repository
- **Mapping**: Conversion between domain and persistence objects
- **Database Queries**: Optimized data access

## Data Flow

### User Registration Flow

1. **API**: Customer sends registration request
2. **Application**: RegisterUserUseCase validates and processes
3. **Domain**: User entity created with validation
4. **Infrastructure**: User saved to database
5. **Domain**: UserRegistered event published
6. **API**: JWT tokens generated and returned

### User Login Flow

1. **API**: Customer sends login request
2. **Application**: LoginUserUseCase validates credentials
3. **Infrastructure**: Database query for user
4. **Domain**: User entity returned
5. **API**: JWT tokens generated and returned

### Token Refresh Flow

1. **API**: Customer sends refresh request
2. **Application**: Validates refresh token
3. **Infrastructure**: Database query for user
4. **API**: New JWT tokens generated and returned

## DDD Principles Applied

### Bounded Context

- **Clear Boundaries**: Authentication operations isolated from other contexts
- **Own Language**: Authentication terminology (login, register, token, role)
- **Independent Evolution**: Can evolve without affecting other contexts

### Aggregates

- **User Aggregate**: Manages user state and lifecycle
- **Consistency**: Aggregate boundaries ensure data consistency

### Value Objects

- **Email**: Immutable with validation
- **Username**: Immutable with format validation
- **Password**: Immutable with security requirements
- **UserId**: Immutable unique identifier

### Domain Events

- **Integration**: Events for other contexts to react
- **Audit**: Complete history of authentication events
- **Decoupling**: Loose coupling between contexts

## Security Considerations

### Authentication

- **JWT Tokens**: Secure token-based authentication
- **Password Hashing**: BCrypt for password security
- **Token Expiration**: Configurable token lifetimes
- **Refresh Tokens**: Secure token refresh mechanism

### Authorization

- **Role-Based Access**: USER and ADMIN roles
- **Endpoint Protection**: Different access levels
- **Admin Controls**: Administrative user management

### Data Protection

- **Password Security**: Never store plain text passwords
- **Token Security**: Secure token generation and storage
- **Input Validation**: All inputs validated and sanitized

## Performance Considerations

### Database Optimization

- **Indexes**: On email, username, and role fields
- **Pagination**: Large user lists paginated
- **Query Optimization**: Efficient user lookups

### Caching Strategy

- **User Caching**: Frequently accessed users cached
- **Token Caching**: Active tokens cached
- **Cache Invalidation**: On user updates

## Extensibility

### New Authentication Methods

- **Social Login**: OAuth2 integration
- **Two-Factor**: SMS or email verification
- **Biometric**: Fingerprint or face recognition

### New User Roles

- **Role Extension**: Add new UserRole values
- **Permission System**: Granular permissions
- **Access Control**: Fine-grained access control

### External Integrations

- **Identity Providers**: LDAP, Active Directory
- **Single Sign-On**: SAML, OAuth2
- **Multi-Tenant**: Organization-based access

## Testing Strategy

### Unit Tests

- **Domain Objects**: Test business logic and validation
- **Use Cases**: Test application layer logic
- **Value Objects**: Test immutability and validation

### Integration Tests

- **Repository Tests**: Test data persistence
- **API Tests**: Test HTTP endpoints
- **Database Tests**: Test with real database

### Security Tests

- **Authentication Tests**: Test login/logout flows
- **Authorization Tests**: Test role-based access
- **Token Tests**: Test JWT token generation and validation

## User Management

### User Lifecycle

- **Registration**: User creates account
- **Activation**: Admin activates user
- **Authentication**: User logs in
- **Profile Updates**: User updates profile
- **Deactivation**: Admin deactivates user

### User States

- **Active**: User can authenticate and use system
- **Inactive**: User cannot authenticate
- **Pending**: User registered but not activated

## Token Management

### Access Token

- **Short-lived**: 15 minutes default
- **Contains**: User ID, role, and claims
- **Usage**: API authentication
- **Storage**: Client-side memory

### Refresh Token

- **Long-lived**: 7 days default
- **Usage**: Generate new access tokens
- **Storage**: HTTP-only cookie
- **Security**: Can be revoked

## Integration Points

### All Contexts

- **Authentication**: Provides user authentication
- **Authorization**: Provides role-based access
- **User Information**: Provides user details

### Security Context

- **Token Validation**: Validates JWT tokens
- **Role Checking**: Checks user roles
- **Access Control**: Controls endpoint access

### Notification Context

- **User Events**: Publishes user-related events
- **Security Events**: Publishes security events
- **Audit Events**: Publishes audit events

## Future Enhancements

### Advanced Security

- **Rate Limiting**: Prevent brute force attacks
- **Account Lockout**: Lock after failed attempts
- **IP Whitelisting**: Restrict access by IP
- **Device Management**: Track and manage devices

### User Experience

- **Remember Me**: Long-term authentication
- **Single Sign-On**: Seamless authentication
- **Social Login**: Easy registration/login
- **Password Reset**: Self-service password reset

### Monitoring and Analytics

- **Login Analytics**: Track login patterns
- **Security Monitoring**: Monitor for attacks
- **User Behavior**: Track user activities
- **Audit Logging**: Complete audit trail
