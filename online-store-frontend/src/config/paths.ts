export const paths = {
  home: {
    path: '/',
    getHref: () => '/',
  },
  auth: {
    register: {
      path: '/auth/register',
      getHref: (redirectTo?: string | null | undefined) =>
        `/auth/register${redirectTo ? `?redirectTo=${encodeURIComponent(redirectTo)}` : ''}`,
    },
    login: {
      path: '/auth/login',
      getHref: (redirectTo?: string | null | undefined) =>
        `/auth/login${redirectTo ? `?redirectTo=${encodeURIComponent(redirectTo)}` : ''}`,
    },
  },
  app: {
    root: {
      path: '/app',
      getHref: () => '/app',
    },
    dashboard: {
      path: 'dashboard',
      getHref: () => '/app/dashboard',
    },
    books: {
      path: '',
      getHref: () => '/app',
    },
    // No book-specific route currently
    cart: {
      path: ':clientEmail/cart',
      getHref: (clientEmail: string) => `/app/${clientEmail}/cart`,
    },
    orders: {
      path: ':clientEmail/orders',
      getHref: (clientEmail: string) => `/app/${clientEmail}/orders`,
    },
    users: {
      path: 'users',
      getHref: () => '/app/users',
    },
    user: {
      path: 'users/:userId',
      getHref: (id: string) => `/app/users/${id}`,
    },
    profile: {
      path: 'profile',
      getHref: () => '/app/profile',
    },
    ordersModeration: {
      path: 'orders/moderation',
      getHref: () => '/app/orders/moderation',
    },
  },
} as const;
