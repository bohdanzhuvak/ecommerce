# Delivery Bounded Context

## Overview

The Delivery bounded context manages order delivery and tracking within the online store system. It provides
comprehensive delivery management including creation, status tracking, and delivery monitoring.

## Key Features

- **Delivery Management**: Create and manage deliveries for orders
- **Status Tracking**: Complete delivery lifecycle from pending to delivered
- **Tracking Numbers**: Unique tracking numbers for each delivery
- **Address Management**: Full delivery address information
- **Status Updates**: Real-time status updates with notes
- **Customer Tracking**: Customers can track their deliveries

## Domain Model

### Entities

#### Delivery

- **DeliveryId**: Unique identifier for the delivery
- **OrderId**: Reference to the associated order
- **UserId**: User who placed the order
- **Address**: Complete delivery address information
- **TrackingNumber**: Unique tracking number for tracking
- **Status**: Current delivery status
- **CreatedAt/UpdatedAt**: Timestamps
- **Notes**: Optional notes for status updates

### Value Objects

- **DeliveryId**: Delivery identifier with validation
- **OrderId**: Order identifier with validation
- **UserId**: User identifier with validation
- **DeliveryAddress**: Complete address information
- **TrackingNumber**: Unique tracking number
- **DeliveryStatus**: Enum for delivery statuses

### Domain Events

- **DeliveryCreated**: Published when delivery is created
- **DeliveryStatusUpdated**: Published when status changes
- **DeliveryDelivered**: Published when delivery is completed

## Business Rules

1. **One Delivery Per Order**: Each order can have only one delivery
2. **Status Progression**: Delivery status must follow valid progression
3. **Address Validation**: Delivery address must be complete and valid
4. **Tracking Number**: Each delivery gets a unique tracking number
5. **User Ownership**: Users can only access their own deliveries

## Use Cases

### Customer Operations

- **CreateDelivery**: Create delivery for an order
- **GetDelivery**: Get delivery details by ID
- **GetDeliveries**: Get all deliveries for a user
- **TrackDelivery**: Track delivery by tracking number

### Admin Operations

- **UpdateDeliveryStatus**: Update delivery status with notes
- **GetAllDeliveries**: View all deliveries with filtering

## API Endpoints

### Customer Endpoints

- `POST /api/customer/deliveries` - Create delivery for order
- `GET /api/customer/deliveries/{id}?userId={userId}` - Get delivery details
- `GET /api/customer/deliveries?userId={userId}&offset={offset}&limit={limit}` - Get user deliveries
- `GET /api/customer/deliveries/track/{trackingNumber}?userId={userId}` - Track delivery

### Admin Endpoints

- `GET /api/admin/deliveries?userId={userId}&status={status}&offset={offset}&limit={limit}` - Get all deliveries
- `PUT /api/admin/deliveries/{id}/status` - Update delivery status

## Integration

### Inbound Integration

- **Order Context**: Receives order information for delivery creation
- **User Context**: Validates user existence

### Outbound Integration

- **Order Context**: Notifies when delivery is completed
- **Notification Context**: Publishes domain events for notifications

## Security Considerations

1. **Authorization**: Only users can access their own deliveries
2. **Validation**: All addresses and data are validated
3. **Tracking**: Secure tracking number generation
4. **Status Updates**: Only authorized users can update status

## Database Schema

### deliveries table

- `id` (VARCHAR, PRIMARY KEY)
- `order_id` (VARCHAR, NOT NULL, UNIQUE)
- `user_id` (VARCHAR, NOT NULL)
- `street` (VARCHAR, NOT NULL)
- `city` (VARCHAR, NOT NULL)
- `state` (VARCHAR, NOT NULL)
- `postal_code` (VARCHAR, NOT NULL)
- `country` (VARCHAR, NOT NULL)
- `recipient_name` (VARCHAR, NOT NULL)
- `phone_number` (VARCHAR, NOT NULL)
- `tracking_number` (VARCHAR, NOT NULL, UNIQUE)
- `status` (VARCHAR(20), NOT NULL)
- `created_at` (TIMESTAMP, NOT NULL)
- `updated_at` (TIMESTAMP, NOT NULL)
- `notes` (VARCHAR(1000))

## Delivery Status Flow

```
PENDING → PICKED_UP → IN_TRANSIT → OUT_FOR_DELIVERY → DELIVERED
    ↓         ↓           ↓              ↓
  FAILED ← RETURNED ← RETURNED ← RETURNED
```

## Future Enhancements

- **Real-time Tracking**: GPS tracking integration
- **Delivery Time Estimates**: Estimated delivery times
- **Delivery Notifications**: SMS/Email notifications
- **Delivery Preferences**: User delivery preferences
- **Multiple Attempts**: Support for multiple delivery attempts
- **Signature Confirmation**: Digital signature capture
- **Photo Confirmation**: Delivery photo confirmation
