export type BaseEntity = {
  id: string;
  createdAt: number;
};

export type Entity<T> = {
  [K in keyof T]: T[K];
} & BaseEntity;

export type Meta = {
  page: number;
  total: number;
  totalPages: number;
};

export type User = Entity<{
  email: string;
  name: string;
  role: 'EMPLOYEE' | 'CLIENT';
  balance?: number;
  phone?: string;
  birthDate?: string;
}>;

export type Client = Entity<{
  email: string;
  name: string;
  balance: number;
}>;

export type Employee = Entity<{
  email: string;
  name: string;
  phone: string;
  birthDate: string;
}>;

export type AuthResponse = {
  token: string;
  user: User;
};

export type Comment = Entity<{
  body: string;
  author: User;
}>;

export interface Book {
  name: string;
  genre: string;
  ageGroup: AgeGroup;
  price: number;
  publicationDate: string;
  author: string;
  pages: number;
  characteristics: string;
  description: string;
  language: Language;
}

export type AgeGroup = 'CHILD' | 'TEEN' | 'ADULT' | 'OTHER';
export type Language = 'ENGLISH' | 'SPANISH' | 'FRENCH' | 'GERMAN' | 'JAPANESE' | 'UKRAINIAN' | 'OTHER';

export interface JwtRefreshResponse {
  token: string;
}