import { defineConfig } from 'orval';

export default defineConfig({
  'online-store-api': {
    input: {
      target: 'http://localhost:8080/v3/api-docs',
      filters: {
        tags: [
          'authentication',
          'products-customer',
          'categories-customer',
          'balance-customer',
          'delivery-customer',
          'orders-customer',
          'cart',
          'users-customer',
        ],
      },
    },
    output: {
      mode: 'tags-split',
      target: './src/shared/api/generated',
      schemas: './src/shared/api/generated/model',
      client: 'react-query',
      prettier: true,
      override: {
        mutator: {
          path: './src/shared/lib/api-client.ts',
          name: 'customInstance',
        },
        query: {
          useQuery: true,
          useMutation: true,
          signal: true,
        },
        operations: {
          // Remove userId from query params for operations with @CurrentUserId
          getUserProfile: {
            query: {
              useQuery: true,
            },
          },
          getBalance: {
            query: {
              useQuery: true,
            },
          },
          getTransactions: {
            query: {
              useQuery: true,
            },
          },
          getOrders: {
            query: {
              useQuery: true,
            },
          },
          getDeliveries: {
            query: {
              useQuery: true,
            },
          },
          getCart: {
            query: {
              useQuery: true,
            },
          },
        },
      },
    },
  },
});
