export const paths = {
  home: {
    path: '/',
    getHref: () => '/',
  },
  products: {
    path: '/',
    getHref: () => '/',
  },
  product: {
    path: 'products/:id',
    getHref: (id: number | string) => `/products/${id}`,
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
      path: '/',
      getHref: () => '/',
    },
    cart: {
      path: '/cart',
      getHref: () => `/cart`,
    },
    orders: {
      root: {
        path: '/orders',
        getHref: () => `/orders`,
      },
      order: {
        path: ':orderId',
        getHref: (orderId: string) => `/orders/${orderId}`,
      },
    },
    user: {
      path: '/users/:userId',
      getHref: (id: string) => `/users/${id}`,
    },
    profile: {
      path: '/profile',
      getHref: () => `/profile`,
    }
  },
} as const;
