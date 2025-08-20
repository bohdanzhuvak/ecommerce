import {QueryClient, useQueryClient} from '@tanstack/react-query';
import {useMemo} from 'react';
import {createBrowserRouter, RouterProvider} from 'react-router';

import {paths} from '@/config/paths';
import {AdminRoute, ProtectedRoute, UserRoute} from '@/shared/lib/auth/protected-route';

import {AppRoot, AppRootErrorBoundary} from './routes/app/root';

export const createAppRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    {
      path: paths.products.path,
      lazy: async () => {
        const {CatalogRoute, catalogLoader} = await import('./routes/app/catalog');
        return {Component: () => (<CatalogRoute/>), loader: catalogLoader(queryClient)};
      },
      ErrorBoundary: AppRootErrorBoundary,
    },
    {
      path: paths.product.path,
      lazy: async () => {
        const {ProductDetailsRoute, productLoader} = await import('./routes/app/product');
        return {Component: () => (<ProductDetailsRoute/>), loader: productLoader(queryClient)};
      },
      ErrorBoundary: AppRootErrorBoundary,
    },
    {
      path: paths.auth.register.path,
      lazy: async () => {
        const {RegisterRoute} = await import('./routes/auth/register');
        return {Component: RegisterRoute};
      },
    },
    {
      path: paths.auth.login.path,
      lazy: async () => {
        const {LoginRoute} = await import('./routes/auth/login');
        return {Component: LoginRoute};
      },
    },
    {
      path: paths.app.root.path,
      element: (
        <ProtectedRoute>
          <UserRoute>
            <AppRoot/>
          </UserRoute>
        </ProtectedRoute>
      ),
      ErrorBoundary: AppRootErrorBoundary,
      children: [
        {
          path: paths.app.cart.path,
          lazy: async () => {
            const {CartRoute, cartLoader} = await import('./routes/app/cart/cart');
            return {Component: () => (<CartRoute/>), loader: cartLoader(queryClient)};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.orders.path,
          lazy: async () => {
            const {OrdersRoute, ordersLoader} = await import('./routes/app/orders/orders');
            return {Component: () => (<OrdersRoute/>), loader: ordersLoader(queryClient)};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.profile.path,
          lazy: async () => {
            const {ProfileRoute} = await import('./routes/app/profile');
            return {Component: ProfileRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
      ],
    },
    {
      path: paths.admin.root.path,
      element: (
        <ProtectedRoute>
          <AdminRoute>
            <AppRoot/>
          </AdminRoute>
        </ProtectedRoute>
      ),
      ErrorBoundary: AppRootErrorBoundary,
      children: [
        {
          path: paths.admin.dashboard.path,
          lazy: async () => {
            const {DashboardRoute} = await import('./routes/app/dashboard');
            return {Component: DashboardRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.admin.users.path,
          lazy: async () => {
            const {UsersRoute, usersLoader} = await import(
              './routes/app/users'
              );
            return {
              Component: UsersRoute,
              loader: usersLoader(queryClient),
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.admin.orders.path,
          lazy: async () => {
            const {AdminOrdersRoute, adminOrdersLoader} = await import('./routes/app/admin/orders');
            return {
              Component: () => (<AdminOrdersRoute/>),
              loader: adminOrdersLoader(queryClient)
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.admin.products.path,
          lazy: async () => {
            const {AdminProductsRoute, adminProductsLoader} = await import('./routes/app/admin/products');
            return {
              Component: () => (<AdminProductsRoute/>),
              loader: adminProductsLoader(queryClient)
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.admin.categories.path,
          lazy: async () => {
            const {AdminCategoriesRoute, adminCategoriesLoader} = await import('./routes/app/admin/categories');
            return {
              Component: () => (<AdminCategoriesRoute/>),
              loader: adminCategoriesLoader(queryClient)
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.admin.profile.path,
          lazy: async () => {
            const {ProfileRoute} = await import('./routes/app/profile');
            return {Component: ProfileRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
      ]
    },
    {
      path: '*',
      lazy: async () => {
        const {NotFoundRoute} = await import('./routes/not-found');
        return {Component: NotFoundRoute};
      },
      ErrorBoundary: AppRootErrorBoundary,
    },
  ]);

export const AppRouter = () => {
  const queryClient = useQueryClient();

  const router = useMemo(() => createAppRouter(queryClient), [queryClient]);

  return <RouterProvider router={router}/>;
};
