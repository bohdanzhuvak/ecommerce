# Cart Bounded Context

This bounded context is responsible for managing user shopping carts in the online store.

## Architecture

Follows Domain-Driven Design (DDD) principles with clear layer separation:

### Domain Layer

- **Entities**: `Cart` - main cart entity
- **Value Objects**: `CartId`, `UserId`, `CartItem`
- **Domain Events**: `CartItemAdded`, `CartItemRemoved`, `CartItemUpdated`, `CartCleared`
- **Repository Interface**: `CartRepository`

### Application Layer

- **Use Cases**:
    - **Customer**: `GetCartUseCase`, `AddItemToCartUseCase`, `UpdateCartItemUseCase`, `RemoveItemFromCartUseCase`,
      `ClearCartUseCase`
- **Application Service**: `CartApplicationService` - orchestrates Use Cases

### API Layer

- **Customer Controller**: `CartController` - REST API for customers

### Infrastructure Layer

- **JPA Entity**: `CartEntity`, `CartItemEntity` - JPA representations
- **JPA Repository**: `JpaCartRepository` - Spring Data JPA repository
- **Repository Implementation**: `CartRepositoryImpl` - domain repository implementation
- **Mapper**: `CartMapper` - mapping between domain objects and JPA entities

## Key Features

### For Customers:

- View their cart contents
- Add items to cart (with automatic price validation from catalog)
- Update item quantities
- Remove items from cart
- Clear entire cart
- Automatic cart creation when first item is added
- Product availability validation

## API Endpoints

### Customer API (`/api/customer/cart`)

- `GET /` - get cart contents
- `POST /items` - add item to cart
- `PUT /items/{productId}` - update item quantity
- `DELETE /items/{productId}` - remove item from cart
- `DELETE /` - clear entire cart

## Domain Rules

1. **Cart ID** - unique cart identifier (UUID)
2. **User ID** - each cart belongs to exactly one user
3. **Cart Items** - each item has product ID, name, unit price, and quantity
4. **Quantity** - must be positive integer
5. **Auto-creation** - cart is created automatically when first item is added
6. **One cart per user** - each user can have only one active cart
7. **Price Security** - product prices are always loaded from catalog, never accepted from client
8. **Product Validation** - only active and available products can be added to cart

## Business Operations

### Add Item to Cart

```java
// Product info is loaded from catalog service
ProductInfo productInfo = catalogService.getProductInfo(productId);
cart.

addItem(productId, productInfo, quantity);
```

### Update Item Quantity

```java
cart.updateItemQuantity(productId, newQuantity);
```

### Remove Item from Cart

```java
cart.removeItem(productId);
```

### Clear Cart

```java
cart.clear();
```

### Calculate Total Price

```java
Money totalPrice = cart.getTotalPrice();
```

### Check if Cart is Empty

```java
boolean isEmpty = cart.isEmpty();
```

## Integration with Other Contexts

- **Catalog Context**: uses `ProductId` and `Money` from catalog domain
- **Order Context**: cart contents can be converted to order items
- **UserAccount Context**: uses `UserId` for cart ownership

## Domain Events

- `CartItemAdded` - published when item is added to cart
- `CartItemRemoved` - published when item is removed from cart
- `CartItemUpdated` - published when item quantity is updated
- `CartCleared` - published when cart is cleared

These events can be used by other bounded contexts for reactive updates (e.g., inventory management, analytics).

## Security Considerations

- Only authenticated users can access their own cart
- Cart operations are scoped to the authenticated user
- No admin access to user carts (privacy protection)
- **Price Security**: Product prices are always loaded from catalog service, never accepted from client
- **Product Validation**: Only active and available products can be added to cart
- **Data Integrity**: Cart items always reflect current product information from catalog
