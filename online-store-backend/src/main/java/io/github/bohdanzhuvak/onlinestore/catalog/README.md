# Catalog Bounded Context

This bounded context is responsible for managing the product catalog in the online store.

## Architecture

Follows Domain-Driven Design (DDD) principles with clear layer separation:

### Domain Layer

- **Entities**: `Product` - main product entity
- **Value Objects**: `ProductId`, `Money`, `CategoryId`
- **Domain Events**: `ProductCreated`, `ProductUpdated`, `ProductDeleted`
- **Repository Interface**: `ProductRepository`

### Application Layer

- **Use Cases**:
    - **Customer**: `GetProductsUseCase`, `GetProductUseCase`, `SearchProductsUseCase`
    - **Admin**: `CreateProductUseCase`, `UpdateProductUseCase`, `DeleteProductUseCase`, `GetAllProductsUseCase`
- **Application Service**: `ProductApplicationService` - orchestrates Use Cases

### API Layer

- **Customer Controller**: `ProductCustomerController` - REST API for customers
- **Admin Controller**: `ProductAdminController` - REST API for administrators

### Infrastructure Layer

- **JPA Entity**: `ProductEntity` - JPA representation of product
- **JPA Repository**: `JpaProductRepository` - Spring Data JPA repository
- **Repository Implementation**: `ProductRepositoryImpl` - domain repository implementation
- **Mapper**: `ProductMapper` - mapping between domain objects and JPA entities

## Key Features

### For Customers:

- View active products with pagination
- Search products by name
- Filter by category
- Filter by price range
- View only available products (in stock)

### For Administrators:

- CRUD operations with products
- View all products (including inactive)
- Manage product stock
- Manage product images

## API Endpoints

### Customer API (`/api/customer/products`)

- `GET /` - get list of active products
- `GET /{id}` - get product by ID
- `GET /search` - search products with parameters
- `GET /available` - get only available products

### Admin API (`/api/admin/products`)

- `GET /` - get all products
- `GET /{id}` - get product by ID
- `POST /` - create new product
- `PUT /{id}` - update product
- `DELETE /{id}` - delete product

## Domain Rules

1. **Product ID** - unique product identifier (UUID)
2. **Name** - product name cannot be empty
3. **Price** - price cannot be negative, supports currency
4. **Stock** - stock cannot be negative
5. **Category** - product must belong to a category
6. **Active** - product is active by default when created
7. **Images** - product can have multiple images

## Business Operations

### Reserve Product Stock

```java
product.reserveStock(quantity);
```

### Release Product Stock

```java
product.releaseStock(quantity);
```

### Check Availability

```java
boolean available = product.isAvailable(); // active && stock > 0
boolean hasStock = product.hasStock(quantity);
```

## Integration with Other Contexts

- **Cart Context**: uses `ProductId` to add products to cart
- **Order Context**: uses `ProductId` and `Money` to create orders
- **UserAccount Context**: can receive notifications about product events

## Domain Events

- `ProductCreated` - published when product is created
- `ProductUpdated` - published when product is updated
- `ProductDeleted` - published when product is deleted

These events can be used by other bounded contexts for reactive updates.
