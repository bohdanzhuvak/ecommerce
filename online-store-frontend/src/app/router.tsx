import {QueryClient, useQueryClient} from '@tanstack/react-query';
import {useMemo} from 'react';
import {createBrowserRouter, RouterProvider} from 'react-router';

import {paths} from '@/config/paths';
import {ProtectedRoute} from '@/shared/lib/auth/protected-route';

import {AppRoot, AppRootErrorBoundary} from './routes/app/root';

export const createAppRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    {
      path: paths.home.path,
      lazy: async () => {
        const {LandingRoute} = await import('./routes/landing');
        return {Component: LandingRoute};
      },
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
          <AppRoot/>
        </ProtectedRoute>
      ),
      ErrorBoundary: AppRootErrorBoundary,
      children: [
        {
          path: paths.app.cart.path,
          lazy: async () => {
            const {CartRoute, cartLoader} = await import(
              './routes/app/cart/cart'
              );
            return {
              Component: CartRoute,
              loader: cartLoader(queryClient),
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.orders.path,
          lazy: async () => {
            const {OrdersRoute, ordersLoader} = await import(
              './routes/app/orders/orders'
              );
            return {
              Component: OrdersRoute,
              loader: ordersLoader(queryClient),
            };
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.ordersModeration.path,
          lazy: async () => {
            const {ModerationOrdersRoute} = await import('./routes/app/orders/moderation');
            return {Component: ModerationOrdersRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.users.path,
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
          path: paths.app.profile.path,
          lazy: async () => {
            const {ProfileRoute} = await import('./routes/app/profile');
            return {Component: ProfileRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
        {
          path: paths.app.dashboard.path,
          lazy: async () => {
            const {DashboardRoute} = await import('./routes/app/dashboard');
            return {Component: DashboardRoute};
          },
          ErrorBoundary: AppRootErrorBoundary,
        },
      ],
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
