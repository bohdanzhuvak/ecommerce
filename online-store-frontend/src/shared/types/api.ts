export interface BaseEntity {
  id: string;
  createdAt: number;
}

export type Meta = {
  page: number;
  total: number;
  totalPages: number;
};

export type User = {
  id: number;
  username: string;
  email: string;
  role: typeof ROLES[keyof typeof ROLES];
};

export type AuthResponse = {
  token: string;
  role: typeof ROLES[keyof typeof ROLES];
};

export interface Comment extends BaseEntity {
  body: string;
  author: User;
}

export interface JwtRefreshResponse {
  token: string;
}

export const ROLES = {
  ADMIN: 'ADMIN',
  USER: 'USER',
} as const;
