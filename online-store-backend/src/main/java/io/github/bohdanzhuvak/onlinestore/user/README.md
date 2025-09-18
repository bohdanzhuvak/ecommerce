# User Bounded Context

This bounded context handles user management, authentication, and profile operations in the online store system.

## Overview

The User bounded context is responsible for:

- User registration and authentication
- User profile management
- Role-based access control (Customer, Admin)
- User account activation/deactivation

## Key Features

- **User Registration**: New users can register with email and password
- **Profile Management**: Users can view and update their profile information
- **Password Management**: Secure password change functionality
- **Role Management**: Support for Customer and Admin roles
- **Account Status**: Users can be activated or deactivated by administrators

## Domain Rules

- Email addresses must be unique across the system
- Passwords must be at least 8 characters long
- User roles are immutable after creation (for security)
- Only administrators can create users with Admin role
- Users can only update their own profile information

## Business Operations

### Customer Operations

- **Register**: Create new user account
- **Get Profile**: View user profile information
- **Update Profile**: Modify first name and last name
- **Change Password**: Update password with current password verification

### Admin Operations

- **Create User**: Create new user with specified role
- **Get All Users**: List users with filtering and pagination
- **Activate User**: Enable user account
- **Deactivate User**: Disable user account

## Domain Model

### Entities

- **User**: Main aggregate root containing user information

### Value Objects

- **UserId**: Unique identifier for users
- **Email**: Validated email address
- **Password**: Secure password handling
- **UserRole**: Enum for user roles (CUSTOMER, ADMIN)

### Domain Events

- **UserCreated**: Published when a new user is registered
- **UserUpdated**: Published when user profile is updated
- **UserActivated**: Published when user account is activated
- **UserDeactivated**: Published when user account is deactivated

## API Endpoints

### Customer Endpoints

- `POST /api/customer/users/register` - Register new user
- `GET /api/customer/users/{userId}` - Get user profile
- `PUT /api/customer/users/{userId}/profile` - Update profile
- `PUT /api/customer/users/{userId}/password` - Change password

### Admin Endpoints

- `POST /api/admin/users` - Create user
- `GET /api/admin/users` - Get all users (with filtering)
- `PUT /api/admin/users/{userId}/activate` - Activate user
- `PUT /api/admin/users/{userId}/deactivate` - Deactivate user

## Security Considerations

- Passwords are hashed before storage (implementation needed for production)
- Email validation ensures proper format
- Role-based access control prevents unauthorized operations
- Users can only access their own profile information

## Integration with Other Contexts

- **Cart Context**: Uses UserId for cart ownership
- **Order Context**: Uses UserId for order ownership
- **UserAccount Context**: May share user information for balance management

## Future Enhancements

- Email verification for new registrations
- Password reset functionality
- Two-factor authentication
- User preferences and settings
- Audit logging for user operations
