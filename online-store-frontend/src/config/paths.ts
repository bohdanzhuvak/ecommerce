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
    path: '/products/:id',
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
      path: 'cart',
      getHref: () => `/cart`,
    },
    orders: {
      path: 'orders',
      getHref: () => `/orders`,
    },
    user: {
      path: 'users/:userId',
      getHref: (id: string) => `/users/${id}`,
    },
    profile: {
      path: 'profile',
      getHref: () => '/profile',
    }
  },
  admin: {
    root: {
      path: '/admin',
      getHref: () => '/admin',
    },
    products: {
      path: 'products',
      getHref: () => '/admin/products',
    },
    categories: {
      path: 'categories',
      getHref: () => '/admin/categories',
    },
    orders: {
      path: 'orders',
      getHref: () => '/admin/orders',
    },
    users: {
      path: 'users',
      getHref: () => '/admin/users',
    },
    dashboard: {
      path: 'dashboard',
      getHref: () => '/admin/dashboard',
    },
    profile: {
      path: 'profile',
      getHref: () => '/admin/profile',
    }
  },
} as const;
