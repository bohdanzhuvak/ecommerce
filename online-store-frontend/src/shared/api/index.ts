/**
 * API Client Entry Point
 *
 * This file re-exports all generated API hooks and types from Orval.
 * The API is automatically generated from the OpenAPI specification at runtime.
 *
 * Usage:
 * ```typescript
 * import { useGetProducts, useCreateOrder } from '@/shared/api';
 *
 * function ProductList() {
 *   const { data, isLoading } = useGetProducts();
 *   // ...
 * }
 * ```
 *
 * To regenerate the API client:
 * ```bash
 * npm run generate:api
 * ```
 *
 * For development with auto-regeneration:
 * ```bash
 * npm run generate:api:watch
 * ```
 */

// Re-export all generated API hooks and types
// Files will be generated after running: npm run generate:api

// Authentication
export * from './generated/authentication/authentication';

// Products
export * from './generated/products-customer/products-customer';

// Re-export with better names for convenience
export * from './generated/products-customer/products-customer';

// Categories
export * from './generated/categories-customer/categories-customer';

// Cart
export * from './generated/cart/cart';

// Orders
export * from './generated/orders-customer/orders-customer';

// Balance
export * from './generated/balance-customer/balance-customer';

// Delivery
export * from './generated/delivery-customer/delivery-customer';

// Users
export * from './generated/users-customer/users-customer';

// Models (types)
export * from './generated/model';
