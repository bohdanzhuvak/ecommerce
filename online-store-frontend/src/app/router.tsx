import {QueryClient, useQueryClient} from '@tanstack/react-query';
import {useMemo} from 'react';
import {createBrowserRouter, RouterProvider} from 'react-router';

import {paths} from '@/config/paths';
import {AdminRoute, AuthenticatedRoute, AuthRoute, LandingRoute, UserRoute} from '@/shared/lib/auth';

import {PublicRoot, PublicRootErrorBoundary} from './routes/public/root';
import {UserRoot, UserRootErrorBoundary} from './routes/user/root';
import {AdminRoot, AdminRootErrorBoundary} from './routes/admin/root';
import {AuthRoot} from "@/app/routes/auth/root.tsx";

export const createAppRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    // Public routes (landing page, catalog)
    {
      path: paths.products.path,
      element: (
        <LandingRoute>
          <PublicRoot/>
        </LandingRoute>
      ),
      ErrorBoundary: PublicRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const {CatalogRoute, catalogLoader} = await import('./routes/public/catalog');
            return {Component: () => (<CatalogRoute/>), loader: catalogLoader(queryClient)};
          },
        },
        {
          path: 'products/:id',
          lazy: async () => {
            const {ProductDetailsRoute, productLoader} = await import('./routes/public/product');
            return {Component: () => (<ProductDetailsRoute/>), loader: productLoader(queryClient)};
          },
        },
      ],
    },

    // Auth routes (login/register)
    {
      path: paths.auth.register.path,
      element: (
        <AuthRoute>
          <AuthRoot/>
        </AuthRoute>
      ),
      children: [
        {
          index: true,
          lazy: async () => {
            const {RegisterRoute} = await import('./routes/auth/register');
            return {Component: RegisterRoute};
          },
        },
      ],
    },
    {
      path: paths.auth.login.path,
      element: (
        <AuthRoute>
          <AuthRoot/>
        </AuthRoute>
      ),
      children: [
        {
          index: true,
          lazy: async () => {
            const {LoginRoute} = await import('./routes/auth/login');
            return {Component: LoginRoute};
          },
        },
      ],
    },

    // User routes (cart, orders, profile)
    {
      path: paths.app.cart.path,
      element: (
        <UserRoute>
          <UserRoot/>
        </UserRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const {CartRoute, cartLoader} = await import('./routes/user/cart');
            return {Component: () => (<CartRoute/>), loader: cartLoader(queryClient)};
          },
        },
      ],
    },
    {
      path: paths.app.orders.path,
      element: (
        <UserRoute>
          <UserRoot/>
        </UserRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const {OrdersRoute, ordersLoader} = await import('./routes/user/orders');
            return {Component: () => (<OrdersRoute/>), loader: ordersLoader(queryClient)};
          },
        },
      ],
    },
    {
      path: paths.app.profile.path,
      element: (
        <UserRoute>
          <UserRoot/>
        </UserRoute>
      ),
      ErrorBoundary: UserRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const {ProfileRoute} = await import('./routes/user/profile');
            return {Component: ProfileRoute};
          },
        },
      ],
    },

    // Admin routes
    {
      path: paths.admin.root.path,
      element: (
        <AdminRoute>
          <AdminRoot/>
        </AdminRoute>
      ),
      ErrorBoundary: AdminRootErrorBoundary,
      children: [
        {
          index: true,
          lazy: async () => {
            const {DashboardRoute} = await import('./routes/admin/dashboard');
            return {Component: DashboardRoute};
          },
        },
        {
          path: paths.admin.users.path,
          lazy: async () => {
            const {UsersRoute, usersLoader} = await import('./routes/admin/users');
            return {
              Component: UsersRoute,
              loader: usersLoader(queryClient),
            };
          },
        },
        {
          path: paths.admin.orders.path,
          lazy: async () => {
            const {AdminOrdersRoute, adminOrdersLoader} = await import('./routes/admin/orders');
            return {
              Component: () => (<AdminOrdersRoute/>),
              loader: adminOrdersLoader(queryClient)
            };
          },
        },
        {
          path: paths.admin.products.path,
          lazy: async () => {
            const {AdminProductsRoute, adminProductsLoader} = await import('./routes/admin/products');
            return {
              Component: () => (<AdminProductsRoute/>),
              loader: adminProductsLoader(queryClient)
            };
          },
        },
        {
          path: paths.admin.categories.path,
          lazy: async () => {
            const {AdminCategoriesRoute, adminCategoriesLoader} = await import('./routes/admin/categories');
            return {
              Component: () => (<AdminCategoriesRoute/>),
              loader: adminCategoriesLoader(queryClient)
            };
          },
        },
        {
          path: paths.admin.profile.path,
          element: (
            <AuthenticatedRoute>
              <UserRoot/>
            </AuthenticatedRoute>
          ),
          children: [
            {
              index: true,
              lazy: async () => {
                const {ProfileRoute} = await import('./routes/user/profile');
                return {Component: ProfileRoute};
              },
            },
          ],
        },
      ]
    },

    // Catch all route
    {
      path: '*',
      lazy: async () => {
        const {NotFoundRoute} = await import('./routes/not-found');
        return {Component: NotFoundRoute};
      },
      ErrorBoundary: PublicRootErrorBoundary,
    },
  ]);

export const AppRouter = () => {
  const queryClient = useQueryClient();

  const router = useMemo(() => createAppRouter(queryClient), [queryClient]);

  return <RouterProvider router={router}/>;
};
