# Online Shop Project Documentation

*Date:* 2025-09-17\
*Version:* 1.0\
*Authors:* Development Team

---

## 1. General Information

The online shop supports:

- Client frontend (product browsing, placing orders)
- Admin panel using React-admin (CRUD products, manage orders, user balances)
- Monolith on Java Spring Boot with DDD architecture

---

## 2. Bounded Contexts / Domains

| Context         | Responsibility                   | Main Use Cases                                               | Domain Events                                                |
|-----------------|----------------------------------|--------------------------------------------------------------|--------------------------------------------------------------|
| **Catalog**     | Products, categories, attributes | Client: view, filterAdmin: CRUD                              | ProductCreated, ProductUpdated, ProductDeleted               |
| **Cart**        | User cart                        | Add/remove items, calculate total, manage quantities         | CartItemAdded, CartItemRemoved, CartItemUpdated, CartCleared |
| **User**        | User management, authentication  | Registration, profile management, role management            | UserCreated, UserUpdated, UserActivated, UserDeactivated     |
| **Order**       | Order placement, statuses        | Client: PlaceOrder, PayOrderAdmin: CancelOrder, ChangeStatus | OrderPlaced, OrderPaid, OrderCancelled                       |
| **Payment**     | Order payments                   | Pay from balance, card payments                              | FundsDebited, PaymentFailed                                  |
| **UserAccount** | Users, balance, roles            | Registration, manage balance and roles                       | UserRegistered, BalanceChanged                               |
| **Delivery**    | Delivery                         | Create shipment, tracking                                    | ShipmentCreated, ShipmentDelivered                           |

---

## 3. Entities and Value Objects

### Catalog

- **Entity:** Product (id, name, description, price, stock, categoryId, active, images, timestamps)
- **Value Objects:** ProductId, Money, CategoryId
- **Domain Events:** ProductCreated, ProductUpdated, ProductDeleted

### Cart

- **Entity:** Cart (id, userId, items, timestamps)
- **Value Objects:** CartId, UserId, CartItem (productId, productName, unitPrice, quantity)
- **Domain Events:** CartItemAdded, CartItemRemoved, CartItemUpdated, CartCleared

### User

- **Entity:** User (id, email, password, firstName, lastName, role, active, timestamps)
- **Value Objects:** UserId, Email, Password, UserRole
- **Domain Events:** UserCreated, UserUpdated, UserActivated, UserDeactivated

### Order

- **Entity:** Order (id, userId, status, total, lines)
- **Value Objects:** OrderLine (productId, quantity, price), Money
- **Enum:** OrderStatus {CREATED, PAID, CANCELLED}

### UserAccount

- **Entity:** UserAccount (id, email, balance, role)
- **Value Objects:** Email, Money

### Payment

- **Entity:** PaymentTransaction (id, orderId, amount, status)
- **Enum:** PaymentStatus {PENDING, COMPLETED, FAILED}

### Delivery

- **Entity:** Shipment (id, orderId, status, trackingNumber)
- **Enum:** ShipmentStatus {CREATED, SHIPPED, DELIVERED}

---

## 4. Main Use Cases (Application Layer)

### Client

- Catalog: view products, filter products
- Cart: add/remove items, view cart
- User: register, view profile, update profile, change password
- Order: place order, pay order
- UserAccount: view balance, recharge balance

### Admin

- Catalog: add/update/delete products
- User: create users, manage roles, activate/deactivate users
- Order: cancel order, change status
- UserAccount: adjust balance, manage roles

---

## 5. Domain Events

| Event             | Source              | Description                     |
|-------------------|---------------------|---------------------------------|
| ProductCreated    | Catalog             | New product created             |
| ProductUpdated    | Catalog             | Product updated                 |
| ProductDeleted    | Catalog             | Product deleted                 |
| CartItemAdded     | Cart                | Item added to cart              |
| CartItemRemoved   | Cart                | Item removed from cart          |
| UserCreated       | User                | New user registered             |
| UserUpdated       | User                | User profile updated            |
| UserActivated     | User                | User account activated          |
| UserDeactivated   | User                | User account deactivated        |
| OrderPlaced       | Order               | Order created                   |
| OrderPaid         | Order               | Order paid                      |
| OrderCancelled    | Order               | Order cancelled                 |
| FundsDebited      | Payment/UserAccount | Funds debited from user account |
| PaymentFailed     | Payment             | Payment failed                  |
| ShipmentCreated   | Delivery            | Shipment created                |
| ShipmentDelivered | Delivery            | Shipment delivered              |

---

## 6. Domain Interaction Example

**Scenario: Paying for an Order using User Balance**

1. Client places an order → `OrderPlaced` (Order domain)
2. Payment service subscribes to `OrderPlaced` → initiates funds debit
3. UserAccount verifies balance → if sufficient, debits funds → publishes `FundsDebited`
4. Payment service confirms successful payment → publishes `OrderPaid`
5. Order updates status to **PAID**

*Domain events ensure loose coupling and asynchronous communication between contexts.*

---

## 7. API Endpoints and Roles

| Role   | Endpoint                      | Domain        | Method              | Description                  |
|--------|-------------------------------|---------------|---------------------|------------------------------|
| Client | /api/customer/products        | Catalog       | GET                 | View products                |
| Client | /api/customer/cart            | Cart          | GET/POST/DELETE     | Manage cart                  |
| Client | /api/customer/users           | User          | POST/GET/PUT        | Register, profile management |
| Client | /api/customer/orders          | Order         | POST                | Place order                  |
| Client | /api/customer/orders/{id}/pay | Order/Payment | POST                | Pay order                    |
| Admin  | /api/admin/products           | Catalog       | GET/POST/PUT/DELETE | CRUD for react-admin         |
| Admin  | /api/admin/users              | User          | GET/POST/PUT        | User management              |
| Admin  | /api/admin/orders             | Order         | GET/PUT             | Manage orders                |
| Admin  | /api/admin/user-accounts      | UserAccount   | GET/PUT             | Manage balance and roles     |

---

## 8. Project Architecture (Layers)

```
Domain Layer (pure business logic)
    ├── Entities
    ├── Value Objects
    ├── Domain Events
    └── Domain Services

Application Layer (Use Cases / Scenarios)
    ├── Client Use Cases
    └── Admin Use Cases

API Layer (REST Controllers)
    ├── Client Controllers
    └── Admin Controllers

Infrastructure Layer (Implementations)
    ├── Repositories (JPA, SQL)
    ├── Event Bus / Messaging
    └── External Integrations
```

---

## 9. Diagrams (Placeholders)

- **Bounded Context Diagram**\


- **Sequence Diagram: Order Payment**\


- **Class Diagram (Entities + Value Objects)**\

*(Can be created using draw\.io, Lucidchart, or PlantUML)*

---

## 10. Best Practices Implemented

- Domain objects are independent of Spring and infrastructure.
- Application Services (Use Cases) orchestrate scenarios, not business rules.
- Admin CRUD is isolated in `admin` subpackage within each bounded context.
- Domain events enable loose coupling and cross-context integration.
- API endpoints separated by `/client/` and `/admin/` with different DTOs.
- Value Objects used for Money, Email, IDs.
- Repositories: interfaces in domain, implementations in infrastructure.
- Supports future extraction of bounded contexts into microservices without changing business logic.

---

## 11. Recommendations for Future Development

- Implement asynchronous event handlers for external integrations.
- Apply transactional boundaries at the use-case level.
- Document new domain events as features are added.
- Use DTOs and mappers for admin CRUD API to keep react-admin independent of internal models.

```
```
