import { QueryClient, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router';

import { paths } from '@/config/paths';
import { AuthRoute, LandingRoute, ProtectedRoute } from '@/shared/lib/auth';

import { PublicRoot, PublicRootErrorBoundary } from './routes/public/root';
import { UserRoot, UserRootErrorBoundary } from './routes/user/root';
import { AuthRoot } from '@/app/routes/auth/root.tsx';

export const createAppRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    // Public routes (landing page, catalog)
    {
      path: paths.products.path,
      element: (
        <LandingRoute>
          <PublicRoot />
        </LandingRoute>
      ),
      ErrorBoundary: PublicRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const { CatalogRoute, catalogLoader } = await import(
              './routes/public/catalog'
            );
            return {
              Component: () => <CatalogRoute />,
              loader: catalogLoader(queryClient),
            };
          },
        },
        {
          path: 'products/:id',
          lazy: async () => {
            const { ProductDetailsRoute, productLoader } = await import(
              './routes/public/product'
            );
            return {
              Component: () => <ProductDetailsRoute />,
              loader: productLoader(queryClient),
            };
          },
        },
      ],
    },

    // Auth routes (login/register)
    {
      path: paths.auth.register.path,
      element: (
        <AuthRoute>
          <AuthRoot />
        </AuthRoute>
      ),
      children: [
        {
          index: true,
          lazy: async () => {
            const { RegisterRoute } = await import('./routes/auth/register');
            return { Component: RegisterRoute };
          },
        },
      ],
    },
    {
      path: paths.auth.login.path,
      element: (
        <AuthRoute>
          <AuthRoot />
        </AuthRoute>
      ),
      children: [
        {
          index: true,
          lazy: async () => {
            const { LoginRoute } = await import('./routes/auth/login');
            return { Component: LoginRoute };
          },
        },
      ],
    },

    // User routes (cart, orders, profile)
    {
      path: paths.app.cart.path,
      element: (
        <ProtectedRoute>
          <UserRoot />
        </ProtectedRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const { CartRoute, cartLoader } = await import(
              './routes/user/cart'
            );
            return {
              Component: () => <CartRoute />,
              loader: cartLoader(queryClient),
            };
          },
        },
      ],
    },
    {
      path: paths.app.orders.root.path,
      element: (
        <ProtectedRoute>
          <UserRoot />
        </ProtectedRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const { OrdersRoute, ordersLoader } = await import(
              './routes/user/orders'
            );
            return {
              Component: () => <OrdersRoute />,
              loader: ordersLoader(queryClient),
            };
          },
        },
        {
          path: paths.app.orders.order.path,
          lazy: async () => {
            const { OrderDetailsRoute, orderDetailsLoader } = await import(
              './routes/user/order-details'
            );
            return {
              Component: () => <OrderDetailsRoute />,
              loader: orderDetailsLoader(queryClient),
            };
          },
        },
      ],
    },
    {
      path: paths.app.profile.path,
      element: (
        <ProtectedRoute>
          <UserRoot />
        </ProtectedRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const { ProfileRoute } = await import('./routes/user/profile');
            return { Component: ProfileRoute };
          },
        },
      ],
    },

    // Catch all route
    {
      path: '*',
      lazy: async () => {
        const { NotFoundRoute } = await import('./routes/not-found');
        return { Component: NotFoundRoute };
      },
      ErrorBoundary: PublicRootErrorBoundary,
    },
  ]);

export const AppRouter = () => {
  const queryClient = useQueryClient();

  const router = useMemo(() => createAppRouter(queryClient), [queryClient]);

  return <RouterProvider router={router} />;
};
