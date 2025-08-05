export interface BaseEntity {
  id: string;
  createdAt: number;
}

export type Meta = {
  page: number;
  total: number;
  totalPages: number;
};

export interface User extends BaseEntity {
  email: string;
  name: string;
  role: 'EMPLOYEE' | 'CLIENT';
  balance?: number;
  phone?: string;
  birthDate?: string;
}

export type AuthResponse = {
  token: string;
  user: User;
};

export interface Comment extends BaseEntity {
  body: string;
  author: User;
}

export interface JwtRefreshResponse {
  token: string;
}
