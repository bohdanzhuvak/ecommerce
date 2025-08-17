export interface BaseEntity {
  id: string;
  createdAt: number;
}
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

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

export interface JwtRefreshResponse {
  token: string;
}

export const ROLES = {
  ADMIN: 'ADMIN',
  USER: 'USER',
} as const;
