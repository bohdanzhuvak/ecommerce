# Auth Bounded Context

## Overview

The Auth bounded context manages user authentication and authorization within the online store system. It provides
comprehensive user management including registration, login, profile management, and administrative user operations.

## Key Features

- **User Registration**: Secure user registration with validation
- **User Authentication**: Login with email/password authentication
- **Token Management**: JWT access and refresh token handling
- **Profile Management**: User profile updates and management
- **User Administration**: Admin operations for user management
- **Role-Based Access**: USER and ADMIN role management
- **Security**: Password hashing and secure token generation

## Domain Model

### Entities

#### User

- **UserId**: Unique identifier for the user
- **Username**: Unique username with validation
- **Email**: Unique email address with validation
- **Password**: Hashed password for security
- **Role**: User role (USER or ADMIN)
- **CreatedAt/UpdatedAt**: Timestamps
- **Active**: User activation status

### Value Objects

- **UserId**: User identifier with UUID validation
- **Username**: Username with format and length validation
- **Email**: Email address with format validation
- **Password**: Hashed password for security
- **AccessToken**: JWT access token with expiration
- **RefreshToken**: JWT refresh token with expiration
- **UserRole**: Enum for user roles

### Domain Events

- **UserRegistered**: Published when user registers
- **UserLoggedIn**: Published when user logs in
- **UserLoggedOut**: Published when user logs out
- **UserActivated**: Published when user is activated
- **UserDeactivated**: Published when user is deactivated

## Business Rules

1. **Unique Identifiers**: Username and email must be unique
2. **Password Security**: Passwords must be hashed and secure
3. **Email Validation**: Email must be in valid format
4. **Username Validation**: Username must follow specific format rules
5. **Role Management**: Users can have USER or ADMIN roles
6. **User Activation**: Users can be activated/deactivated by admins

## Use Cases

### Customer Operations

- **RegisterUser**: Register new user account
- **LoginUser**: Authenticate user with credentials
- **GetUser**: Get user profile information
- **UpdateUserProfile**: Update user profile information

### Admin Operations

- **GetAllUsers**: View all users with filtering
- **ActivateUser**: Activate user account
- **DeactivateUser**: Deactivate user account

## API Endpoints

### Customer Endpoints

- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/refresh` - Refresh access token
- `GET /api/v1/auth/me` - Get current user info
- `POST /api/v1/auth/logout` - User logout

### Admin Endpoints

- `GET /api/admin/users?role={role}&offset={offset}&limit={limit}` - Get all users
- `POST /api/admin/users/{userId}/activate` - Activate user
- `POST /api/admin/users/{userId}/deactivate` - Deactivate user

## Integration

### Inbound Integration

- **Security Context**: Provides authentication for all other contexts
- **User Context**: Manages user information and profiles

### Outbound Integration

- **All Contexts**: Provides user authentication and authorization
- **Notification Context**: Publishes domain events for notifications

## Security Considerations

1. **Password Hashing**: All passwords are hashed using BCrypt
2. **JWT Tokens**: Secure token generation and validation
3. **Token Expiration**: Access and refresh tokens have expiration times
4. **Role-Based Access**: Different access levels for USER and ADMIN
5. **Input Validation**: All inputs are validated and sanitized

## Database Schema

### users table

- `id` (VARCHAR, PRIMARY KEY)
- `username` (VARCHAR, NOT NULL, UNIQUE)
- `email` (VARCHAR, NOT NULL, UNIQUE)
- `password` (VARCHAR, NOT NULL)
- `role` (VARCHAR(20), NOT NULL)
- `created_at` (TIMESTAMP, NOT NULL)
- `updated_at` (TIMESTAMP, NOT NULL)
- `active` (BOOLEAN, NOT NULL)

## Authentication Flow

### Login Flow

1. User provides email and password
2. System validates credentials
3. System generates access and refresh tokens
4. Tokens are returned to client
5. Refresh token is stored in HTTP-only cookie

### Registration Flow

1. User provides username, email, and password
2. System validates all inputs
3. System checks for existing users
4. System creates new user account
5. System generates tokens and returns them

### Token Refresh Flow

1. Client sends refresh token
2. System validates refresh token
3. System generates new access and refresh tokens
4. New tokens are returned to client

## Password Security

### Password Requirements

- Minimum 6 characters
- Must be hashed before storage
- Cannot be stored in plain text

### Hashing Strategy

- Use BCrypt for password hashing
- Salt rounds: 12 (configurable)
- Never store plain text passwords

## Token Management

### Access Token

- Short-lived (15 minutes default)
- Contains user information and role
- Used for API authentication
- Stored in memory on client

### Refresh Token

- Long-lived (7 days default)
- Used to generate new access tokens
- Stored in HTTP-only cookie
- Can be revoked for security

## User Roles

### USER Role

- Can access customer endpoints
- Can manage own profile
- Can place orders and manage cart
- Cannot access admin endpoints

### ADMIN Role

- Can access all endpoints
- Can manage all users
- Can view all orders and deliveries
- Can adjust user balances

## Future Enhancements

- **Two-Factor Authentication**: SMS or email verification
- **Social Login**: Google, Facebook, GitHub integration
- **Password Reset**: Email-based password reset
- **Account Lockout**: Lock account after failed attempts
- **Session Management**: Track active sessions
- **Audit Logging**: Log all authentication events
- **OAuth2 Integration**: Third-party authentication
- **Multi-Factor Authentication**: Hardware tokens, authenticator apps
